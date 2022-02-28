package com.github.sebastianfrey.joa.services.gpkg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import com.github.sebastianfrey.joa.models.FeatureQuery;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Queryables;
import com.github.sebastianfrey.joa.models.Schemas;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.models.Spatial;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;
import com.github.sebastianfrey.joa.models.schema.JSONSchemaBuilder;
import com.github.sebastianfrey.joa.models.schema.type.GenericType;
import com.github.sebastianfrey.joa.models.schema.type.ObjectType;
import com.github.sebastianfrey.joa.services.OGCAPIService;
import com.github.sebastianfrey.joa.utils.CrsUtils;
import com.google.common.io.MoreFiles;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.sqlite.SQLiteConnection;
import org.sqlite.core.DB;
import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.GeoPackageException;
import mil.nga.geopackage.GeoPackageManager;
import mil.nga.geopackage.contents.Contents;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureResultSet;
import mil.nga.geopackage.features.user.FeatureRow;
import mil.nga.geopackage.geom.GeoPackageGeometryData;
import mil.nga.geopackage.user.ColumnValue;
import mil.nga.sf.GeometryEnvelope;
import mil.nga.sf.GeometryType;
import mil.nga.sf.geojson.Feature;
import mil.nga.sf.geojson.FeatureConverter;

/**
 * GeoPackage specific OGCAPIService implementation powerd by
 * <a href="https://github.com/ngageoint/geopackage-java">National Geospatial-Intelligence Agency's
 * (NGA)</a> GeoPackage implementation.
 *
 * @author sfrey
 */
public class GeoPackageService implements OGCAPIService {
  private File workspace;
  private String runtime;

  public GeoPackageService(String workspace, String runtime) {
    this.workspace = new File(workspace);
    this.runtime = runtime;
  }

  @Override
  public Services getServices() {
    Services services = new Services();

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(workspace.toPath(), "*.gpkg")) {
      for (Path path : stream) {
        if (!Files.isDirectory(path)) {
          String fileName = path.getFileName().toString();
          String serviceId = fileName.replaceAll(".gpkg", "");

          Service service = new Service().serviceId(serviceId).title(serviceId);

          services.addService(service);
        }
      }
    } catch (IOException ex) {
      throw new NotFoundException("Failed to fetch services.", ex);
    }

    return services;
  }

  public Service getService(String serviceId) {
    if (!exists(serviceId)) {
      throw new NotFoundException("Service with ID '" + serviceId + "' does not exist.");
    }

    return new Service().serviceId(serviceId).title(serviceId);
  }

  @Override
  public Conformance getConformance(String serviceId) {
    Conformance conformance = new Conformance().serviceId(serviceId)
        .conformsTo(Conformance.FEATURES_CORE)
        .conformsTo(Conformance.FEATURES_OAS30)
        .conformsTo(Conformance.FEATURES_GEOJSON)
        .conformsTo(Conformance.FEATURES_HTML);

    return conformance;
  }

  /**
   * Returns all collections for a given GeoPackage aka Service.
   *
   * @param serviceId The name of the GeoPackage file.
   *
   * @return
   */
  @Override
  public Collections getCollections(String serviceId) {
    Collections collections = new Collections().serviceId(serviceId).title(serviceId);

    try (GeoPackage gpkg = loadService(serviceId)) {

      List<String> collectionIds = gpkg.getFeatureTables();

      for (String collectionId : collectionIds) {
        Collection collection = getCollection(gpkg, serviceId, collectionId);

        collections.collection(collection);
      }
    }

    return collections;
  }

  /**
   * Returns a collection by its name for a given GeoPackage aka Service.
   *
   * @param serviceId
   * @param collectionId
   * @return
   * @throws IOException
   */
  @Override
  public Collection getCollection(String serviceId, String collectionId) {
    try (GeoPackage gpkg = loadService(serviceId)) {
      return getCollection(gpkg, serviceId, collectionId);
    }
  }

  /**
   * Returns a collection by its name for a given GeoPackage aka Service.
   *
   * @param gpkg
   * @param serviceId
   * @param collectionId
   * @return
   * @throws IOException
   */
  private Collection getCollection(GeoPackage gpkg, String serviceId, String collectionId) {
    FeatureDao featureDao = loadCollection(gpkg, collectionId);
    return createCollection(serviceId, featureDao);
  }

  /**
   * Return all items of a collection from a given service.
   *
   * @param serviceId
   * @param collectionId
   * @return
   */
  @Override
  public GeoPackageItems getItems(String serviceId, String collectionId, FeatureQuery query)
      throws Exception {
    GeoPackageItems items = new GeoPackageItems().serviceId(serviceId)
        .collectionId(collectionId)
        .queryString(query.getQueryString())
        .offset(query.getOffset())
        .limit(query.getLimit());

    try (GeoPackage gpkg = loadService(serviceId)) {
      FeatureDao featureDao = loadCollection(gpkg, collectionId);

      GeoPackageQueryResult result = new GeoPackageQuery(featureDao, query).execute();

      String geometryType = null;
      switch (featureDao.getGeometryType()) {
        case POINT:
          geometryType = "Point";
          break;
        case LINESTRING:
          geometryType = "LineString";
          break;
        case POLYGON:
          geometryType = "Polygon";
          break;
        case MULTIPOINT:
          geometryType = "MultiPoint";
          break;
        case MULTILINESTRING:
          geometryType = "MultiLineString";
          break;
        case MULTIPOLYGON:
          geometryType = "MultiPolygon";
          break;
        default:
          break;
      }

      items.numberMatched(result.getCount())
          .geometryType(geometryType)
          .idColumn(featureDao.getIdColumnName());

      GeometryEnvelope bbox = null;

      FeatureResultSet featureResultSet = result.getFeatureResultSet();
      try {
        while (featureResultSet.moveToNext()) {
          FeatureRow featureRow = featureResultSet.getRow();

          Feature feature = createFeature(featureRow);
          if (feature != null) {
            items.feature(feature);
          }

          GeometryEnvelope envelope = createEnvelope(featureRow);
          if (envelope != null) {
            if (bbox == null) {
              bbox = envelope.copy();
            } else {
              bbox = bbox.union(envelope);
            }
          }
        }
      } finally {
        featureResultSet.close();
      }

      if (bbox != null) {
        if (bbox.is3D()) {
          items.bbox(List.of(bbox.getMinX(), bbox.getMinY(), bbox.getMinZ(), bbox.getMaxX(),
              bbox.getMaxY(), bbox.getMaxZ()));
        } else {
          items.bbox(List.of(bbox.getMinX(), bbox.getMinY(), bbox.getMaxX(), bbox.getMaxY()));
        }
      }
    }

    return items;
  }

  /**
   * Return a specific item by its id from a given service.
   *
   * @param serviceId
   * @param collectionId
   * @return
   */
  @Override
  public GeoPackageItem getItem(String serviceId, String collectionId, Long featureId) {
    try (GeoPackage gpkg = loadService(serviceId)) {
      FeatureDao featureDao = loadCollection(gpkg, collectionId);
      FeatureResultSet featureResultSet = featureDao.queryForId(featureId);
      try {
        while (featureResultSet.moveToNext()) {
          FeatureRow featureRow = featureResultSet.getRow();

          Feature feature = createFeature(featureRow);

          return new GeoPackageItem().serviceId(serviceId)
              .collectionId(collectionId)
              .feature(feature);
        }
      } finally {
        featureResultSet.close();
      }
    }

    throw new NotFoundException("Feature with ID '" + featureId + "' does not exist.");
  }

  @Override
  public Queryables getQueryables(String serviceId, String collectionId) {
    try (GeoPackage gpkg = loadService(serviceId)) {
      ObjectType schema =
          JSONSchemaBuilder.objectType().title(collectionId).schema(Schemas.DRAFT_2019_09);

      FeatureDao featureDao = loadCollection(gpkg, collectionId);

      String geometryColumn = featureDao.getGeometryColumnName();

      featureDao.getColumns().stream().forEach((column) -> {
        if (column.isGeometry()) {
          return;
        }

        Long max = column.getMax();
        Object defaultValue = column.getDefaultValue();

        GenericType<?> type = null;

        switch (column.getDataType()) {
          case BOOLEAN:
            type = JSONSchemaBuilder.booleanType();
            break;
          case BLOB:
          case TINYINT:
          case TEXT:
            type = JSONSchemaBuilder.stringType().maxLength(max);
            break;
          case DATE:
            type = JSONSchemaBuilder.stringType().maxLength(max).format("date");
            break;
          case DATETIME:
            type = JSONSchemaBuilder.stringType().maxLength(max).format("date-time");
            break;
          case DOUBLE:
          case FLOAT:
          case REAL:
            type = JSONSchemaBuilder.numberType();
            break;
          case INT:
          case INTEGER:
          case MEDIUMINT:
          case SMALLINT:
            type = JSONSchemaBuilder.integerType().maximum(max);
            break;
          default:
            break;
        }

        if (type == null) {
          return;
        }

        String columnName = column.getName();

        type.title(columnName);

        if (defaultValue != null) {
          type.defaultValue(defaultValue.toString());
        }

        schema.property(columnName, type);
      });

      GeometryType geometryType = featureDao.getGeometryType();

      JSONSchema geometrySchema = null;
      switch (geometryType) {
        case GEOMETRY:
          geometrySchema = Schemas.GeoJSON.geometry();
          break;
        case POINT:
          geometrySchema = Schemas.GeoJSON.point();
          break;
        case LINESTRING:
          geometrySchema = Schemas.GeoJSON.lineString();
          break;
        case POLYGON:
          geometrySchema = Schemas.GeoJSON.polygon();
          break;
        case MULTIPOINT:
          geometrySchema = Schemas.GeoJSON.multiPoint();
          break;
        case MULTILINESTRING:
          geometrySchema = Schemas.GeoJSON.multiLineString();
          break;
        case MULTIPOLYGON:
          geometrySchema = Schemas.GeoJSON.multiPolygon();
          break;
        default:
          break;
      }

      if (geometrySchema != null) {
        schema.property(geometryColumn, geometrySchema);
      }

      return new Queryables().serviceId(serviceId).collectionId(collectionId).schema(schema);
    }
  }

  public GeoPackage loadService(String serviceId) throws WebApplicationException {
    File path = Paths.get(workspace.getAbsolutePath(), serviceId + ".gpkg").toFile();
    GeoPackage gpkg = null;
    try {
      gpkg = GeoPackageManager.open(path);

      // enable spatialite
      loadSpatialiteRuntime(gpkg);

    } catch (SQLException ex) {
      if (gpkg != null) {
        gpkg.close();
      }

      throw new WebApplicationException(ex);
    } catch (GeoPackageException ex) {
      throw new NotFoundException("Service with ID '" + serviceId + "' does not exist.", ex);
    }

    return gpkg;
  }

  private void loadSpatialiteRuntime(GeoPackage gpkg) throws SQLException {
    final Connection con = gpkg.getConnection().getConnection();
    final DB db = ((SQLiteConnection) con).getDatabase();

    try {
      db.enable_load_extension(true);

      try (PreparedStatement pstmt = con.prepareStatement("SELECT load_extension(?)")) {
        pstmt.setString(1, runtime);
        pstmt.executeQuery();
      }

      try (Statement stmt = con.createStatement()) {
        stmt.execute("SELECT EnableGpkgMode()");
      }
    } catch (SQLException ex) {
      throw new SQLException("Failed to load Spatialite extension", ex);
    } finally {
      db.enable_load_extension(false);
    }
  }

  public FeatureDao loadCollection(GeoPackage gpkg, String collectionId) {
    try {
      return gpkg.getFeatureDao(collectionId);
    } catch (GeoPackageException ex) {
      throw new NotFoundException("Collection with ID '" + collectionId + "' does not exist.", ex);
    }
  }

  public boolean exists(String file) {
    return Paths.get(workspace.getAbsolutePath(), file + ".gpkg").toFile().exists();
  }

  public Collection createCollection(String serviceId, FeatureDao featureDao) {
    Contents contents = featureDao.getContents();

    String collectionId = contents.getTableName();
    String title = contents.getIdentifier();
    String description = contents.getDescription();
    String crs = CrsUtils.epsg(contents.getSrsId());
    GeometryEnvelope envelope = contents.getBoundingBox().buildEnvelope();

    Bbox bbox = new Bbox().minX(envelope.getMinX())
        .minY(envelope.getMinY())
        .minZ(envelope.getMinZ())
        .maxX(envelope.getMaxX())
        .maxY(envelope.getMaxY())
        .maxZ(envelope.getMaxZ());

    List<String> temporal = new ArrayList<String>();
    temporal.add(null);
    temporal.add(null);

    Collection collection = new Collection().serviceId(serviceId)
        .collectionId(collectionId)
        .title(title)
        .description(description)
        .crs(crs)
        .itemType("feature")
        .spatial(new Spatial().bbox(bbox).crs(crs))
        .interval(temporal);

    return collection;
  }

  public Feature createFeature(FeatureRow featureRow) {
    Feature feature = null;

    GeoPackageGeometryData geometryData = featureRow.getGeometry();
    if (geometryData != null && !geometryData.isEmpty()) {
      feature = FeatureConverter.toFeature(geometryData.getGeometry());

      feature.setProperties(new HashMap<>());

      for (Map.Entry<String, ColumnValue> entry : featureRow.getAsMap()) {
        ColumnValue value = entry.getValue();
        String key = entry.getKey();
        if (!key.equals(featureRow.getGeometryColumnName())) {
          feature.getProperties().put(key, value.getValue());
        }
      }

      feature.setId(String.valueOf(featureRow.getId()));
    }

    return feature;
  }

  public GeometryEnvelope createEnvelope(FeatureRow featureRow) {
    GeoPackageGeometryData geometryData = featureRow.getGeometry();
    if (geometryData != null && !geometryData.isEmpty()) {
      return geometryData.getGeometry().getEnvelope();
    }
    return null;
  }

  @Override
  public void addService(FormDataBodyPart body) throws InterruptedException {
    for (BodyPart part : body.getParent().getBodyParts()) {
      InputStream fileInputStream = part.getEntityAs(InputStream.class);
      ContentDisposition fileMetaData = part.getContentDisposition();

      try {
        int read = 0;
        byte[] bytes = new byte[1024];

        String fullFileName = fileMetaData.getFileName();

        String fileName = MoreFiles.getNameWithoutExtension(Paths.get(fullFileName));

        File target = Paths.get(workspace.toString(), fullFileName).toFile();

        OutputStream out = new FileOutputStream(target);
        while ((read = fileInputStream.read(bytes)) != -1) {
          out.write(bytes, 0, read);
        }
        out.flush();
        out.close();

        String[] cmd =
            {"ogr2ogr", "-f", "GPKG", fileName + ".gpkg", fullFileName, "-update", "-append"};

        ProcessBuilder b = new ProcessBuilder(cmd);

        b.directory(workspace);

        Process p = b.start();

        p.waitFor();

      } catch (IOException e) {
        throw new WebApplicationException("Error while uploading file. Please try again !!", e);
      }
    }
  }

  @Override
  public void deleteService(String serviceId) {

  }

  @Override
  public void updateService(String serviceId, FormDataBodyPart body) {

  }

}
