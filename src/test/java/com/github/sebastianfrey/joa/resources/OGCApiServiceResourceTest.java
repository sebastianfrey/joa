package com.github.sebastianfrey.joa.resources;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.sebastianfrey.joa.extensions.jackson.LinkDeserializer;
import com.github.sebastianfrey.joa.extensions.jackson.LinkSerializer;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.models.Spatial;
import com.github.sebastianfrey.joa.services.OGCApiService;
import com.github.sebastianfrey.joa.utils.CrsUtils;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import javax.ws.rs.core.Link;

@ExtendWith(DropwizardExtensionsSupport.class)
public class OGCApiServiceResourceTest {

  @SuppressWarnings("unchecked")
  private static final OGCApiService<Object, Object> DAO = mock(OGCApiService.class);

  private static final ResourceExtension EXT = ResourceExtension.builder()
      .addProvider(MultiPartFeature.class)
      .addProvider(DeclarativeLinkingFeature.class)
      .addResource(new OGCApiServiceResource(DAO))
      .build();

  static {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Link.class, new LinkSerializer());
    module.addDeserializer(Link.class, new LinkDeserializer());
    EXT.getObjectMapper().registerModule(module);
  }

  private Services services;
  private Service service;
  private Collections collections;
  private Collection collection;
  private Bbox bbox;
  private Spatial spatial;

  @BeforeEach
  void setup() {
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
  }

  @AfterEach
  @SuppressWarnings("unchecked")
  void tearDown() {
    reset(DAO);
  }

  @Test
  public void should_return_the_available_services() {
    when(DAO.getServices()).thenReturn(services);

    Services found = EXT.target("/").request().get(Services.class);

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

    // verify that ogcApisService.getServices(...) was called
    verify(DAO).getServices();
  }

  @Test
  public void should_return_the_available_service() {
    when(DAO.getService("service1")).thenReturn(service);

    Service found = EXT.target("/service1").request().get(Service.class);

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

    // verify that ogcApisService.getService(...) was called
    verify(DAO).getService("service1");
  }


  @Test
  public void should_return_the_available_collections() {
    when(DAO.getCollections("service1")).thenReturn(collections);

    Collections found = EXT.target("/service1/collections").request().get(Collections.class);

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

    // verify that ogcApisService.getCollections(...) was called
    verify(DAO).getCollections("service1");
  }

  @Test
  public void should_return_the_available_collection() throws Exception {
    when(DAO.getCollection("service1", "collection1")).thenReturn(collection);

    Collection found =
        EXT.target("/service1/collections/collection1").request().get(Collection.class);

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

    // verify that ogcApisService.getCollection(...) was called
    verify(DAO).getCollection("service1", "collection1");
  }
}
