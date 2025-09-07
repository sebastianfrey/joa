package com.github.sebastianfrey.joa.services.gpkg;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Crs;
import com.github.sebastianfrey.joa.models.Datetime;
import com.github.sebastianfrey.joa.models.Queryables;
import com.github.sebastianfrey.joa.models.Schemas;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.resources.request.ItemQueryRequest;
import com.github.sebastianfrey.joa.resources.request.ItemsQueryRequest;
import com.github.sebastianfrey.joa.utils.CrsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mil.nga.sf.geojson.Point;
import static org.assertj.core.api.Assertions.*;

public class GeoPackageServiceTest {
  public static final String TEST_SERVICE = "example";
  public static final String TEST_COLLECTION_POINT = "point1";
  public static final String TEST_COLLECTION_LINE = "line1";
  public static final String TEST_COLLECTION_POLYGON = "polygon1";

  private static File getTestDirectory(String path) throws Exception {
    URL resourceUrl = GeoPackageServiceTest.class.getResource(path);
    Path resourcePath = Paths.get(resourceUrl.toURI());
    File file = resourcePath.toFile();
    return file;
  }

  private GeoPackageService geoPackageService;


  @BeforeEach
  void setup() throws Exception {
    geoPackageService =
        new GeoPackageService(getTestDirectory("/").getAbsolutePath(), "mod_spatialite");
  }

  @Test
  public void should_return_services() {
    Services services = geoPackageService.getServices();

    assertThat(services).isNotNull();
    assertThat(services.getServices()).isNotEmpty();
  }

  @Test
  public void should_throw_when_services_directory_is_not_available() throws Exception {
    GeoPackageService invalidService =
        new GeoPackageService(new File("/not_a_directory/").getAbsolutePath(), "mod_spatialite");

    assertThatThrownBy(() -> invalidService.getServices()).isInstanceOf(NotFoundException.class);
  }

  @Test
  public void should_throw_when_spatialite_runtime_can_not_be_loaded() throws Exception {
    GeoPackageService invalidService =
        new GeoPackageService(getTestDirectory("/").getAbsolutePath(), "spatialite");

    assertThatThrownBy(() -> invalidService.loadService(TEST_SERVICE))
        .isInstanceOf(InternalServerErrorException.class);
  }

  @Test
  public void should_return_service() {
    Service service = geoPackageService.getService(TEST_SERVICE);

    assertThat(service).isNotNull();
    assertThat(service.getServiceId()).isEqualTo("example");
    assertThat(service.getTitle()).isEqualTo("example");
  }

  @Test
  public void should_throw_when_service_is_not_found() {
    assertThatThrownBy(() -> geoPackageService.getService("notaservice"))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  public void should_return_conformance_classes() {
    Conformance conformance = geoPackageService.getConformance(TEST_SERVICE);

    assertThat(conformance).isNotNull();
    assertThat(conformance.getServiceId()).isEqualTo("example");
    assertThat(conformance.getConformsTo()).isNotEmpty();
  }

  @Test
  public void should_return_collections_from_service() {
    Collections collections = geoPackageService.getCollections(TEST_SERVICE);

    assertThat(collections).isNotNull();
    assertThat(collections.getServiceId()).isEqualTo("example");
    assertThat(collections.getTitle()).isEqualTo("example");
    assertThat(collections.getCollections()).isNotEmpty();
  }

  @Test
  public void should_throw_when_collection_is_not_found() {
    assertThatThrownBy(() -> geoPackageService.getCollection("notaservice", "notacollection"))
        .isInstanceOf(NotFoundException.class);
    assertThatThrownBy(() -> geoPackageService.getCollection("example", "notacollection"))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  public void should_return_collection_from_service() {
    Collection collection = geoPackageService.getCollection(TEST_SERVICE, TEST_COLLECTION_POINT);

    assertThat(collection).isNotNull();
    assertThat(collection.getServiceId()).isEqualTo("example");
    assertThat(collection.getCollectionId()).isEqualTo("point1");
    assertThat(collection.getId()).isEqualTo("point1");
    assertThat(collection.getTitle()).isEqualTo("point1");
    assertThat(collection.getExtent()).satisfies((extent) -> {
      assertThat(extent.getSpatial().getBbox()).isNotEmpty();
      assertThat(extent.getTemporal().getInterval()).isNotEmpty();
    });
  }

  @Test
  public void should_return_queryables_from_collection_of_points() {
    Queryables queryables = geoPackageService.getQueryables(TEST_SERVICE, TEST_COLLECTION_POINT);

    assertThat(queryables).isNotNull();
    assertThat(queryables.getServiceId()).isEqualTo("example");
    assertThat(queryables.getCollectionId()).isEqualTo("point1");
    assertThat(queryables.getSchema()).satisfies((schema) -> {
      assertThat(schema.getProperties()).isNotEmpty();
      assertThat(schema.getProperties()).containsKey("geometry");
      assertThat(schema.getProperties()).containsValue(Schemas.GeoJSON.point());
    });
  }

  @Test
  public void should_return_queryables_from_collection_of_lines() {
    Queryables queryables = geoPackageService.getQueryables(TEST_SERVICE, TEST_COLLECTION_LINE);

    assertThat(queryables).isNotNull();
    assertThat(queryables.getServiceId()).isEqualTo("example");
    assertThat(queryables.getCollectionId()).isEqualTo("line1");
    assertThat(queryables.getSchema()).satisfies((schema) -> {
      assertThat(schema.getProperties()).isNotEmpty();
      assertThat(schema.getProperties()).containsKey("geometry");
      assertThat(schema.getProperties()).containsValue(Schemas.GeoJSON.lineString());
    });
  }

  @Test
  public void should_return_queryables_from_collection_of_polygons() {
    Queryables queryables = geoPackageService.getQueryables(TEST_SERVICE, TEST_COLLECTION_POLYGON);

    assertThat(queryables).isNotNull();
    assertThat(queryables.getServiceId()).isEqualTo("example");
    assertThat(queryables.getCollectionId()).isEqualTo("polygon1");
    assertThat(queryables.getSchema()).satisfies((schema) -> {
      assertThat(schema.getProperties()).isNotEmpty();
      assertThat(schema.getProperties()).containsKey("geometry");
      assertThat(schema.getProperties()).containsValue(Schemas.GeoJSON.polygon());
    });
  }

  @Test
  public void should_throw_when_collection_is_not_found_for_queryables() {
    assertThatThrownBy(() -> geoPackageService.getQueryables("notaservice", "notacollection"))
        .isInstanceOf(NotFoundException.class);
    assertThatThrownBy(() -> geoPackageService.getQueryables("example", "notacollection"))
        .isInstanceOf(NotFoundException.class);
  }


  @Test
  public void should_return_items_from_all_collections() throws Exception {
    final ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0)).limit(10);

    for (final String TEST_COLLECTION : List.of(TEST_COLLECTION_POINT, TEST_COLLECTION_LINE,
        TEST_COLLECTION_POLYGON)) {
      GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION, query);

      assertThat(items).isNotNull();
      assertThat(items.getServiceId()).isEqualTo(TEST_SERVICE);
      assertThat(items.getCollectionId()).isEqualTo(TEST_COLLECTION);
      assertThat(items.getFeatures()).isNotEmpty();
      assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
      assertThat(items.getNumberReturned()).isGreaterThan(0);
      assertThat(items.getBbox()).isNotEmpty();
      assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
    }
  }

  @Test
  public void should_return_items_from_collection_with_bbox_query() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .bbox(new Bbox().minX(-180.0).minY(-90.0).maxX(80.0).maxY(90.0));

    GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  }

  @Test
  public void should_return_items_from_collection_with_bboxCrs_query() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .bbox(new Bbox().minX(-20026376.39).minY(-20048966.10).maxX(20026376.39).maxY(20048966.10))
        .bboxCrs(new Crs(CrsUtils.parse(3857)));

    GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  }

  @Test
  public void should_return_items_from_collection_with_exact_datetime_query() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .datetime(new Datetime("2021-03-04T17:44:25.873Z"));

    final GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  }

  @Test
  public void should_return_items_from_collection_with_closed_interval_datetime_query() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .datetime(new Datetime("2021-02-04T00:00:00/2021-04-04T00:00:00Z"));

    final GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  }

  @Test
  public void should_return_items_from_collection_with_start_open_interval_datetime_query() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .datetime(new Datetime("../2021-03-04T17:44:25.873Z"));

    final GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  }

  @Test
  public void should_return_items_from_collection_with_end_open_interval_datetime_query() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .datetime(new Datetime("2021-03-04T17:44:25.873Z/.."));

    final GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  }
/*
  @Test
  public void should_return_items_from_collection_with_after_interval_datetime_query() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .datetime(new Datetime("2021-03-04T17:44:25.873Z/.."));

    final GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  } */

  @Test
  public void should_return_items_from_collection_with_simple_query_parameters() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .queryParameter("integer", "5")
        .queryParameter("boolean", "false", "true");

    GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  }

  @Test
  public void should_return_items_from_collection_with_crs_query_parameter() throws Exception {
    ItemsQueryRequest query = new ItemsQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .crs(new Crs(CrsUtils.CRS84));

    GeoPackageItems items = geoPackageService.getItems(TEST_SERVICE, TEST_COLLECTION_POINT, query);

    assertThat(items).isNotNull();
    assertThat(items.getServiceId()).isEqualTo("example");
    assertThat(items.getCollectionId()).isEqualTo("point1");
    assertThat(items.getFeatures()).isNotEmpty();
    assertThat(items.getNumberMatched()).isGreaterThan(Long.valueOf(0));
    assertThat(items.getNumberReturned()).isGreaterThan(0);
    assertThatCode(() -> Instant.parse(items.getTimeStamp())).doesNotThrowAnyException();
  }

  @Test
  public void should_return_item_from_collection() throws Exception {
    GeoPackageItem item = geoPackageService.getItem(TEST_SERVICE, TEST_COLLECTION_POINT,
        Long.valueOf(1), new ItemQueryRequest());

    assertThat(item).isNotNull();
    assertThat(item.getServiceId()).isEqualTo("example");
    assertThat(item.getCollectionId()).isEqualTo("point1");
    assertThat(item.getId()).isEqualTo("1");
    assertThat(item.getGeometry()).isInstanceOf(Point.class);
    assertThat(item.getProperties()).isNotEmpty();
    assertThat(item.getBbox()).isNull();
  }

  @Test
  public void should_throw_when_item_is_not_found() {
    assertThatThrownBy(() -> geoPackageService.getItem("notaservice", "notacollection",
        Long.valueOf(10000), new ItemQueryRequest())).isInstanceOf(NotFoundException.class);
    assertThatThrownBy(() -> geoPackageService.getItem("example", "notacollection",
        Long.valueOf(10000), new ItemQueryRequest())).isInstanceOf(NotFoundException.class);
    assertThatThrownBy(() -> geoPackageService.getItem("example", "point1", Long.valueOf(10000),
        new ItemQueryRequest())).isInstanceOf(NotFoundException.class);
  }
}
