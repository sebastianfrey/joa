package com.github.sebastianfrey.joa.resources;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.sebastianfrey.joa.extensions.jackson.LinkDeserializer;
import com.github.sebastianfrey.joa.extensions.jackson.LinkSerializer;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Item;
import com.github.sebastianfrey.joa.models.Items;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.models.Queryables;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.models.Spatial;
import com.github.sebastianfrey.joa.resources.exception.QueryParamExceptionHandler;
import com.github.sebastianfrey.joa.resources.filters.AlternateLinksResponseFilter;
import com.github.sebastianfrey.joa.resources.filters.RewriteFormatQueryParamToAcceptHeaderRequestFilter;
import com.github.sebastianfrey.joa.resources.request.ItemQueryRequest;
import com.github.sebastianfrey.joa.resources.request.ItemsQueryRequest;
import com.github.sebastianfrey.joa.services.OGCAPIService;
import com.github.sebastianfrey.joa.utils.CrsUtils;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import mil.nga.sf.geojson.Geometry;
import mil.nga.sf.geojson.Point;
import mil.nga.sf.geojson.Position;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class OGCAPIServiceResourceTest {

  // openapi
  private final static Info info = new Info().title("JOA")
      .description("Java based OGC-API-Features implementation.")
      .license(new License().name("MIT"))
      .version("1.0.0")
      .termsOfService("http://example.com/terms")
      .contact(new Contact().email("sebastian.frey@outlook.com"));

  private final static OpenAPI oas = new OpenAPI().info(info);
  private final static SwaggerConfiguration oasConfig = new SwaggerConfiguration().openAPI(oas)
      .prettyPrint(true)
      .resourcePackages(
          Stream.of("com.github.sebastianfrey.joa.resources").collect(Collectors.toSet()));

  private static final OGCAPIService DAO = mock(OGCAPIService.class);

  private static final ResourceExtension EXT = ResourceExtension.builder()
      .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
      .addProvider(MultiPartFeature.class)
      .addProvider(QueryParamExceptionHandler.class)
      .addProvider(RewriteFormatQueryParamToAcceptHeaderRequestFilter.class)
      .addProvider(DeclarativeLinkingFeature.class)
      .addProvider(AlternateLinksResponseFilter.class)
      .addResource(new OpenApiResource().openApiConfiguration(oasConfig))
      .addResource(new OGCAPIServiceResource(DAO))
      .build();

  static {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Link.class, new LinkSerializer());
    module.addDeserializer(Link.class, new LinkDeserializer());
    EXT.getObjectMapper().registerModule(module);
  }

  private static class TestItems extends Items<TestItems> {
    private boolean firstPageAvailable = false;
    private boolean lastPageAvailable = false;
    private boolean nextPageAvailable = false;
    private boolean prevPageAvailable = false;

    public TestItems() {}

    @Override
    public boolean isFirstPageAvailable() {
      return firstPageAvailable;
    }

    public TestItems firstPageAvailable(boolean firstPageAvailable) {
      this.firstPageAvailable = firstPageAvailable;
      return this;
    }

    @Override
    public boolean isLastPageAvailable() {
      return lastPageAvailable;
    }

    public TestItems lastPageAvailable(boolean lastPageAvailable) {
      this.lastPageAvailable = lastPageAvailable;
      return this;
    }

    @Override
    public boolean isNextPageAvailable() {
      return nextPageAvailable;
    }

    public TestItems nextPageAvailable(boolean nextPageAvailable) {
      this.nextPageAvailable = nextPageAvailable;
      return this;
    }

    @Override
    public boolean isPrevPageAvailable() {
      return prevPageAvailable;
    }

    public TestItems prevPageAvailable(boolean prevPageAvailable) {
      this.prevPageAvailable = prevPageAvailable;
      return this;
    }
  }

  public static class TestItem extends Item<TestItem> {
    private String id;
    private Map<String, Object> properties = new HashMap<>();
    private double[] bbox;
    private Geometry geometry;

    @Override
    public String getId() {
      return id;
    }

    public TestItem id(String id) {
      this.id = id;
      return this;
    }

    @Override
    public double[] getBbox() {
      return bbox;
    }

    public TestItem bbox(double... bbox) {
      this.bbox = bbox;
      return this;
    }

    @Override
    public Map<String, Object> getProperties() {
      return properties;
    }

    public TestItem property(String key, Object value) {
      this.properties.put(key, value);
      return this;
    }

    @Override
    public Geometry getGeometry() {
      return geometry;
    }

    public TestItem geometry(Geometry geometry) {
      this.geometry = geometry;
      return this;
    }
  }

  private Services services;
  private Service service;
  private Collections collections;
  private Collection collection;
  private Conformance conformance;
  private TestItems items;
  private TestItem item;

  private Bbox bbox;
  private Spatial spatial;
  private Queryables queryables;

  @BeforeEach
  void setup() {
    queryables = new Queryables();

    service = new Service().serviceId("service1")
        .title("service1 title")
        .description("service1 description");

    services = new Services().service(service);

    bbox = new Bbox().minX(-104.807496)
        .minY(39.71408499999999)
        .maxX(-104.79948)
        .maxY(39.72001399999999);

    spatial = new Spatial().bbox(bbox).crs(CrsUtils.crs84());

    collection = new Collection().serviceId("service1")
        .collectionId("collection1")
        .crs(CrsUtils.crs84())
        .spatial(spatial)
        .title("collection1 title")
        .description("collection1 description");

    collections =
        new Collections().serviceId("serviceId").title("collections title").collection(collection);

    items = new TestItems().serviceId("service1")
        .collectionId("collection1")
        .numberMatched(Long.valueOf(0));

    item = new TestItem().serviceId("service1").collectionId("collection1");

    conformance = new Conformance().serviceId("service1")
        .conformsTo(Conformance.FEATURES_1_CORE)
        .conformsTo(Conformance.FEATURES_1_GEOJSON)
        .conformsTo(Conformance.FEATURES_1_OAS30);
  }

  @AfterEach
  void tearDown() {
    reset(DAO);
  }

  @Test
  public void should_return_the_services() {
    when(DAO.getServices()).thenReturn(services);

    Services found = EXT.target("/").queryParam("f", "json").request().get(Services.class);

    // verify that ogcApisService.getServices(...) was called
    verify(DAO).getServices();

    assertThat(found.getServices()).satisfies(($services) -> {
      // there must be at least one service
      assertThat($services).isNotEmpty();

      // assert first service
      assertThat($services.get(0)).satisfies(($service) -> {

        // assert service properties
        assertThat($service.getTitle()).isEqualTo(service.getTitle());
        assertThat($service.getDescription()).isEqualTo(service.getDescription());

        // assert service links
        assertThat($service.getLinks()).satisfies((links) -> {
          assertThat(links).isNotEmpty();
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.CONFORMANCE));
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SERVICE_DESC));
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.DATA));
        });
      });
    });

    // assert global links
    assertThat(found.getLinks()).satisfies((links) -> {
      assertThat(links).isNotEmpty();
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
    });
  }

  @Test
  public void should_return_the_requested_service() {
    when(DAO.getService("service1")).thenReturn(service);

    Service found = EXT.target("/service1").queryParam("f", "json").request().get(Service.class);

    // verify that ogcApisService.getService(...) was called
    verify(DAO).getService("service1");

    assertThat(found).isNotNull();

    assertThat(found.getTitle()).isEqualTo(service.getTitle());
    assertThat(found.getDescription()).isEqualTo(service.getDescription());

    assertThat(found.getLinks()).satisfies((links) -> {
      assertThat(links).isNotEmpty();
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SERVICE_DESC)
          && link.getType().equals(MediaType.APPLICATION_OPENAPI_JSON));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SERVICE_DESC)
          && link.getType().equals(MediaType.APPLICATION_OPENAPI_YAML));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.CONFORMANCE));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.DATA));
    });
  }


  @Test
  public void should_return_the_collections() {
    when(DAO.getCollections("service1")).thenReturn(collections);

    Collections found = EXT.target("/service1/collections")
        .queryParam("f", "json")
        .request()
        .get(Collections.class);

    // verify that ogcApisService.getCollections(...) was called
    verify(DAO).getCollections("service1");

    assertThat(found.getTitle()).isEqualTo(collections.getTitle());
    assertThat(found.getDescription()).isEqualTo(collections.getDescription());

    assertThat(found.getCollections()).satisfies(($collections) -> {
      // there must be at least one collection
      assertThat($collections).isNotEmpty();

      assertThat($collections.get(0)).satisfies(($collection) -> {
        assertThat($collection.getId()).isEqualTo(collection.getId());
        assertThat($collection.getItemType()).isEqualTo(collection.getItemType());
        assertThat($collection.getTitle()).isEqualTo(collection.getTitle());
        assertThat($collection.getDescription()).isEqualTo(collection.getDescription());
        assertThat($collection.getCrs()).containsExactlyElementsOf(collection.getCrs());
        assertThat($collection.getExtent()).satisfies(($extent) -> {
          assertThat($extent.getSpatial()).satisfies(($spatial) -> {
            assertThat($spatial.getBbox()).isNotEmpty();
            assertThat($spatial.getBbox().get(0))
                .containsExactlyElementsOf(spatial.getBbox().get(0));
          });
        });
        assertThat($collection.getLinks()).satisfies((links) -> {
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.ITEMS));
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.QUERYABLES));
        });
      });
    });

    assertThat(found.getLinks()).satisfies((links) -> {
      assertThat(links).isNotEmpty();
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
    });
  }

  @Test
  public void should_return_the_conformance_classes() {
    when(DAO.getConformance("service1")).thenReturn(conformance);

    Conformance found = EXT.target("/service1/conformance")
        .queryParam("f", "json")
        .request()
        .get(Conformance.class);

    verify(DAO).getConformance("service1");

    assertThat(found.getConformsTo()).contains(Conformance.FEATURES_1_CORE,
        Conformance.FEATURES_1_GEOJSON, Conformance.FEATURES_1_OAS30);

    assertThat(found.getLinks()).satisfies((links) -> {
      assertThat(links).isNotEmpty();
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
    });
  }

  @Test
  public void should_return_the_service_desc_as_json() {
    String found = EXT.target("/service1/api").queryParam("f", "json").request().get(String.class);

    assertThatCode(() -> Json.mapper().readValue(found.toString(), OpenAPI.class))
        .doesNotThrowAnyException();
  }

  @Test
  public void should_return_the_service_desc_as_yaml() {
    String found = EXT.target("/service1/api").queryParam("f", "yaml").request().get(String.class);

    assertThatCode(() -> Yaml.mapper().readValue(found.toString(), OpenAPI.class))
        .doesNotThrowAnyException();
  }

  @Test
  public void should_return_the_requested_collection() throws Exception {
    when(DAO.getCollection("service1", "collection1")).thenReturn(collection);

    Collection found = EXT.target("/service1/collections/collection1")
        .queryParam("f", "json")
        .request()
        .get(Collection.class);

    // verify that ogcApisService.getCollection(...) was called
    verify(DAO).getCollection("service1", "collection1");

    assertThat(found.getId()).isEqualTo(collection.getId());
    assertThat(found.getItemType()).isEqualTo(collection.getItemType());
    assertThat(found.getTitle()).isEqualTo(collection.getTitle());
    assertThat(found.getDescription()).isEqualTo(collection.getDescription());
    assertThat(found.getCrs()).containsExactlyElementsOf(collection.getCrs());
    assertThat(found.getExtent()).satisfies(($extent) -> {
      assertThat($extent.getSpatial()).satisfies(($spatial) -> {
        assertThat($spatial.getBbox()).isNotEmpty();
        assertThat($spatial.getBbox().get(0)).containsExactlyElementsOf(spatial.getBbox().get(0));
      });
    });

    assertThat(found.getLinks()).satisfies((links) -> {
      assertThat(links).isNotEmpty();
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.ITEMS));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.QUERYABLES));
    });
  }

  @Test
  public void should_return_items() throws Exception {
    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    Items<TestItems> found = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .request()
        .get(TestItems.class);

    assertThat(found.getFeatures()).isEmpty();
    assertThat(found.getTimeStamp()).isNotNull();
    assertThat(found.getNumberReturned()).isEqualTo(0);
    assertThat(found.getNumberMatched()).isEqualTo(Long.valueOf(0));

    assertThat(found.getLinks()).satisfies((links) -> {
      assertThat(links).isNotEmpty();

      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
    });

    ArgumentCaptor<String> serviceIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> collectionIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<ItemsQueryRequest> featureQueryCaptor =
        ArgumentCaptor.forClass(ItemsQueryRequest.class);

    verify(DAO).getItems(serviceIdCaptor.capture(), collectionIdCaptor.capture(),
        featureQueryCaptor.capture());

    // verify captures
    assertThat(serviceIdCaptor.getValue()).isEqualTo("service1");
    assertThat(collectionIdCaptor.getValue()).isEqualTo("collection1");
    assertThat(featureQueryCaptor.getValue()).satisfies((query) -> {
      // validate default parametersvalue
      assertThat(query.getLimit()).isEqualTo(10);
      assertThat(query.getOffset()).isEqualTo(Long.valueOf(0));
      assertThat(query.getBbox()).isNull();
      assertThat(query.getDatetime()).isNull();
      assertThat(query.getQueryString()).isNotEmpty();
      assertThat(query.getQueryParameters()).containsKey("f");
    });
  }

  @Test
  public void should_handle_the_given_query_parameters_for_items() throws Exception {
    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("bbox", "12.0,12.0,13.0,13.0")
        .queryParam("datetime", "2011-04-15T20:08:18Z")
        .queryParam("limit", "20")
        .queryParam("offset", "100")
        .request()
        .get(TestItems.class);

    ArgumentCaptor<String> serviceIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> collectionIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<ItemsQueryRequest> featureQueryCaptor =
        ArgumentCaptor.forClass(ItemsQueryRequest.class);

    verify(DAO).getItems(serviceIdCaptor.capture(), collectionIdCaptor.capture(),
        featureQueryCaptor.capture());

    // verify captures
    assertThat(serviceIdCaptor.getValue()).isEqualTo("service1");
    assertThat(collectionIdCaptor.getValue()).isEqualTo("collection1");
    assertThat(featureQueryCaptor.getValue()).satisfies((query) -> {
      // verify query parameters
      assertThat(query.getLimit()).isEqualTo(20);
      assertThat(query.getOffset()).isEqualTo(Long.valueOf(100));
      assertThat(query.getBbox()).satisfies((bbox) -> {
        assertThat(bbox.getMinX()).isEqualTo(12.0);
        assertThat(bbox.getMinY()).isEqualTo(12.0);
        assertThat(bbox.getMaxX()).isEqualTo(13.0);
        assertThat(bbox.getMaxY()).isEqualTo(13.0);
      });
      assertThat(query.getDatetime()).satisfies((datetime) -> {
        assertThat(datetime.getRawValue()).isEqualTo("2011-04-15T20:08:18Z");
      });
      assertThat(query.getQueryString()).isNotEmpty();
      assertThat(query.getQueryParameters()).isNotEmpty();
    });
  }

  @Test
  public void should_include_no_pagination_links_for_items() throws Exception {
    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    TestItems found = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .request()
        .get(TestItems.class);

    ArgumentCaptor<String> serviceIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> collectionIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<ItemsQueryRequest> featureQueryCaptor =
        ArgumentCaptor.forClass(ItemsQueryRequest.class);

    verify(DAO).getItems(serviceIdCaptor.capture(), collectionIdCaptor.capture(),
        featureQueryCaptor.capture());

    assertThat(serviceIdCaptor.getValue()).isEqualTo("service1");
    assertThat(collectionIdCaptor.getValue()).isEqualTo("collection1");
    assertThat(featureQueryCaptor.getValue()).isNotNull();

    assertThat(found.getLinks()).satisfies((links) -> {
      assertThat(links).isNotEmpty();
      assertThat(links).noneMatch((link) -> link.getRel().equals(Linkable.NEXT));
      assertThat(links).noneMatch((link) -> link.getRel().equals(Linkable.PREV));
      assertThat(links).noneMatch((link) -> link.getRel().equals(Linkable.FIRST));
      assertThat(links).noneMatch((link) -> link.getRel().equals(Linkable.LAST));
    });
  }

  @Test
  public void should_include_pagination_links_for_items() throws Exception {
    items.nextPageAvailable(true)
        .prevPageAvailable(true)
        .firstPageAvailable(true)
        .lastPageAvailable(true);

    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    TestItems found = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .request()
        .get(TestItems.class);

    ArgumentCaptor<String> serviceIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> collectionIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<ItemsQueryRequest> featureQueryCaptor =
        ArgumentCaptor.forClass(ItemsQueryRequest.class);

    verify(DAO).getItems(serviceIdCaptor.capture(), collectionIdCaptor.capture(),
        featureQueryCaptor.capture());

    assertThat(serviceIdCaptor.getValue()).isEqualTo("service1");
    assertThat(collectionIdCaptor.getValue()).isEqualTo("collection1");
    assertThat(featureQueryCaptor.getValue()).isNotNull();

    assertThat(found.getLinks()).satisfies((links) -> {
      assertThat(links).isNotEmpty();
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.NEXT));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.PREV));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.FIRST));
      assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.LAST));
    });
  }

  @Test
  public void should_return_the_requested_item() throws Exception {
    item.bbox(12.0, 12.0, 13.0, 13.0)
        .geometry(new Point(new Position(12.5, 12.5)))
        .id("1")
        .property("number", 1.0)
        .property("integer", 1)
        .property("string", "abc");

    doReturn(item).when(DAO).getItem("service1", "collection1", Long.valueOf(1), new ItemQueryRequest());

    TestItem found = EXT.target("/service1/collections/collection1/items/1")
        .queryParam("f", "json")
        .request()
        .get(TestItem.class);

    verify(DAO).getItem("service1", "collection1", Long.valueOf(1), new ItemQueryRequest());

    assertThat(found.getId()).isNotEmpty();
    assertThat(found.getBbox()).isNotEmpty();
    assertThat(found.getGeometry()).isNotNull();
    assertThat(found.getProperties()).isNotEmpty();
  }

  @Test
  public void should_throw_http_400_for_invalid_limit_query_parameter() throws Exception {
    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    Response response = EXT.target("/service1/collections/collection1/items")
        .queryParam("limit", "12.0")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);

    response = EXT.target("/service1/collections/collection1/items")
        .queryParam("limit", "abc")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);
  }

  @Test
  public void should_throw_http_400_for_invalid_offset_query_parameter() throws Exception {
    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    Response response = EXT.target("/service1/collections/collection1/items")
        .queryParam("offset", "12.0")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);

    response = EXT.target("/service1/collections/collection1/items")
        .queryParam("offset", "abc")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);
  }

  @Test
  public void should_throw_http_400_for_invalid_bbox_query_parameter() throws Exception {
    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    Response response = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("bbox", "12.0")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);

    response = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("bbox", "12.0,12.0,13.0,13.0,160.0")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);

    response = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("bbox", "12.0,12.0,13.0,13.0,160.0,161.0,152.0")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);

    response = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("bbox", "abc")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);
  }

  @Test
  public void should_throw_http_400_for_invalid_datetime_query_parameter() throws Exception {
    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    Response response = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("datetime", "12.0")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);

    response = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("datetime", "2021-15-12")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);

    response = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("datetime", "../..")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);
  }

  @Test
  public void should_throw_http_400_for_invalid_unknown_query_parameter() throws Exception {
    when(DAO.getQueryables("service1", "collection1")).thenReturn(queryables);
    doReturn(items).when(DAO)
        .getItems(eq("service1"), eq("collection1"), any(ItemsQueryRequest.class));

    Response response = EXT.target("/service1/collections/collection1/items")
        .queryParam("f", "json")
        .queryParam("unknownQueryParameter", "abc")
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(400);
  }
}
