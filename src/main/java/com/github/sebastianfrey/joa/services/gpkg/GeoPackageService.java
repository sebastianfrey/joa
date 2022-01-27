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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import com.github.sebastianfrey.joa.models.FeatureQuery;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Item;
import com.github.sebastianfrey.joa.models.Items;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.services.FeatureService;
import com.github.sebastianfrey.joa.services.UploadService;
import com.google.common.io.MoreFiles;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.GeoPackageException;
import mil.nga.geopackage.GeoPackageManager;
import mil.nga.geopackage.contents.Contents;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureResultSet;
import mil.nga.geopackage.features.user.FeatureRow;
import mil.nga.geopackage.geom.GeoPackageGeometryData;
import mil.nga.geopackage.user.ColumnValue;
import mil.nga.sf.geojson.Geometry;
import mil.nga.sf.GeometryEnvelope;
import mil.nga.sf.geojson.Feature;
import mil.nga.sf.geojson.FeatureConverter;

/**
 * GeoPackage specific FeatureService and UploadService implementation powerd by
 * <a href="https://github.com/ngageoint/geopackage-java">National Geospatial-Intelligence Agency's
 * (NGA)</a> GeoPackage implementation.
 *
 * @author sfrey
 */
public class GeoPackageService implements FeatureService<Feature, Geometry>, UploadService {
  private File root;

  public GeoPackageService(String root) {
    this.root = new File(root);
  }

  public Services getServices() {
    Services services = new Services();

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(root.toPath(), "*.gpkg")) {
      for (Path path : stream) {
        if (!Files.isDirectory(path)) {
          String fileName = path.getFileName().toString();
          String serviceId = fileName.replaceAll(".gpkg", "");

          Service service = new Service().serviceId(serviceId).title(serviceId);

          services.addService(service);
        }
      }
    } catch (IOException ex) {
      throw new WebApplicationException(ex, Status.NO_CONTENT);
    }

    return services;
  }

  public Service getService(String serviceId) {
    if (!exists(serviceId)) {
      throw new NotFoundException();
    }

    return new Service().serviceId(serviceId).title(serviceId);
  }

  public Conformance getConformance(String serviceId) {
    Conformance conformance = new Conformance(serviceId);

    conformance.addConformsTo(Conformance.FEATURES_CORE);
    conformance.addConformsTo(Conformance.FEATURES_OAS30);
    conformance.addConformsTo(Conformance.FEATURES_GEOJSON);

    return conformance;
  }

  /**
   * Returns all collections for a given GeoPackage aka Service.
   *
   * @param serviceId The name of the GeoPackage file.
   *
   * @return
   */
  public Collections getCollections(String serviceId) {
    Collections collections = new Collections().serviceId(serviceId).title(serviceId);

    try (GeoPackage gpkg = open(serviceId)) {

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
  public Collection getCollection(String serviceId, String collectionId) {
    try (GeoPackage gpkg = open(serviceId)) {
      return getCollection(gpkg, serviceId, collectionId);
    } catch (GeoPackageException ex) {
      throw new NotFoundException("Collection with ID '" + collectionId + "' does not exist.", ex);
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
  public Collection getCollection(GeoPackage gpkg, String serviceId, String collectionId) {
    return createCollection(serviceId, gpkg.getFeatureDao(collectionId));
  }

  /**
   * Return all items of a collection from a given service.
   *
   * @param serviceId
   * @param collectionId
   * @return
   */
  public Items<Feature> getItems(String serviceId, String collectionId, FeatureQuery query) {
    GeoPackageItems items = new GeoPackageItems().serviceId(serviceId)
        .collectionId(collectionId)
        .queryString(query.getQueryString())
        .offset(query.getOffset())
        .limit(query.getLimit());



    try (GeoPackage gpkg = open(serviceId)) {
      FeatureDao featureDao = gpkg.getFeatureDao(collectionId);

      String orderBy = featureDao.getIdColumnName();
      Integer limit = query.getLimit();
      Long offset = query.getOffset();

      Integer numberMatched = featureDao.count();
      items.numberMatched(numberMatched.longValue());

      FeatureResultSet featureResultSet = featureDao.queryForChunk(orderBy, limit, offset);

      try {
        while (featureResultSet.moveToNext()) {
          FeatureRow featureRow = featureResultSet.getRow();

          Feature feature = createFeature(featureRow);

          if (feature != null) {
            items.feature(feature);
          }
        }
      } finally {
        featureResultSet.close();
      }
    }

    return items;
  }

  public Item<Geometry> getItem(String serviceId, String collectionId, Long featureId) {
    try (GeoPackage gpkg = open(serviceId)) {
      FeatureDao featureDao = gpkg.getFeatureDao(collectionId);

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

  public GeoPackage open(String file) {
    File path = Paths.get(root.getAbsolutePath(), file + ".gpkg").toFile();
    return GeoPackageManager.open(path);
  }

  public boolean exists(String file) {
    return Paths.get(root.getAbsolutePath(), file + ".gpkg").toFile().exists();
  }

  public Collection createCollection(String serviceId, FeatureDao featureDao) {
    Contents contents = featureDao.getContents();

    String collectionId = contents.getTableName();
    String title = contents.getIdentifier();
    String description = contents.getDescription();
    String crs = "http://www.opengis.net/def/crs/EPSG/0/" + contents.getSrsId();
    GeometryEnvelope envelope = contents.getBoundingBox().buildEnvelope();

    Bbox bbox = new Bbox().minX(envelope.getMinX())
        .minY(envelope.getMinY())
        .minZ(envelope.getMinZ())
        .maxX(envelope.getMaxX())
        .maxY(envelope.getMaxY())
        .maxZ(envelope.getMaxZ());

    return new Collection().serviceId(serviceId)
        .collectionId(collectionId)
        .title(title)
        .description(description)
        .crs(crs)
        .itemType("feature")
        .bbox(bbox);
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

        File target = Paths.get(root.toString(), fullFileName).toFile();

        OutputStream out = new FileOutputStream(target);
        while ((read = fileInputStream.read(bytes)) != -1) {
          out.write(bytes, 0, read);
        }
        out.flush();
        out.close();

        String[] cmd =
            {"ogr2ogr", "-f", "GPKG", fileName + ".gpkg", fullFileName, "-update", "-append"};

        ProcessBuilder b = new ProcessBuilder(cmd);

        b.directory(root);

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
