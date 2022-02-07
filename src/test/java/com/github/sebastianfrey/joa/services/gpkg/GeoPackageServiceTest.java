package com.github.sebastianfrey.joa.services.gpkg;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import javax.ws.rs.NotFoundException;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Datetime;
import com.github.sebastianfrey.joa.models.Queryables;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.resources.request.FeatureQueryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mil.nga.sf.geojson.Point;
import static org.assertj.core.api.Assertions.*;

public class GeoPackageServiceTest {
  public static final String TEST_SERVICE = "example";

  public static final String TEST_COLLECTION_POINT = "point1";
  public static final String TEST_COLLECTION_POLYLINE = "polyline1";
  public static final String TEST_COLLECTION_POLYGON = "polygon1";

  private static File getTestDirectory() throws Exception {
    URL resourceUrl = GeoPackageServiceTest.class.getResource("/");
    Path resourcePath = Paths.get(resourceUrl.toURI());
    File file = resourcePath.toFile();
    return file;
  }

  private GeoPackageService geoPackageService;


  @BeforeEach
  void setup() throws Exception {
    geoPackageService =
        new GeoPackageService(getTestDirectory().getAbsolutePath(), "mod_spatialite");
  }

  @Test
  public void should_return_services() {
    Services services = geoPackageService.getServices();

    assertThat(services).isNotNull();
    assertThat(services.getServices()).isNotEmpty();
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
    assertThatThrownBy(() -> geoPackageService.getService("notafile"))
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
  public void should_return_queryables_from_collection() {
    Queryables queryables = geoPackageService.getQueryables(TEST_SERVICE, TEST_COLLECTION_POINT);

    assertThat(queryables).isNotNull();
    assertThat(queryables.getServiceId()).isEqualTo("example");
    assertThat(queryables.getCollectionId()).isEqualTo("point1");
    assertThat(queryables.getSchema().getProperties()).isNotEmpty();
  }

  @Test
  public void should_return_items_from_collection() throws Exception {
    FeatureQueryRequest query = new FeatureQueryRequest().offset(Long.valueOf(0))
        .limit(10)
        .datetime(new Datetime("2021-03-04T17:44:25.873Z"))
        .bbox(new Bbox().minX(-180.0).minY(-90.0).maxX(80.0).maxX(90.0));

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
    GeoPackageItem item = geoPackageService.getItem(TEST_SERVICE, TEST_COLLECTION_POINT, Long.valueOf(1));

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
    assertThatThrownBy(() -> geoPackageService.getItem("example", "point1", Long.valueOf(10000)))
        .isInstanceOf(NotFoundException.class);
  }
}
