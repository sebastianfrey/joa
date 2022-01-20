package com.github.joa.services.geopackage;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import com.github.joa.api.Capabilities;
import com.github.joa.api.Collection;
import com.github.joa.api.Collections;
import com.github.joa.api.Conformance;
import com.github.joa.api.FeatureCollection;
import com.github.joa.api.FeatureQuery;
import com.github.joa.services.CollectionService;
import com.github.joa.util.CollectionUtils;
import com.github.joa.util.FeatureUtils;

import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.GeoPackageException;
import mil.nga.geopackage.GeoPackageManager;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureResultSet;
import mil.nga.geopackage.features.user.FeatureRow;
import mil.nga.sf.geojson.Feature;

public class GeoPackageCollectionService implements CollectionService {

  private File root;

  public GeoPackageCollectionService(String root) {
    this.root = new File(root);
  }

  public List<String> getServices() {
    Set<String> fileList = new HashSet<>();

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(root.toPath())) {
      for (Path path : stream) {
        if (!Files.isDirectory(path)) {
          String fileName = path.getFileName().toString();

          if (fileName.endsWith(".gpkg")) {
            fileName = fileName.replaceAll(".gpkg", "");

            fileList.add(fileName);
          }
        }
      }
    } catch (IOException ex) {
      throw new WebApplicationException(ex, Status.NO_CONTENT);
    }

    return new ArrayList<String>(fileList);
  }

  public Capabilities getCapabilities(String serviceId) {
    if (!exists(serviceId)) {
      throw new NotFoundException();
    }

    Capabilities capabilities = new Capabilities(serviceId);
    return capabilities;
  }

  public Conformance getConformance(String serviceId) {
    Conformance conformance = new Conformance();

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
    Collections collections = new Collections(serviceId);

    try (GeoPackage gpkg = open(serviceId)) {

      List<String> collectionIds = gpkg.getFeatureTables();

      for (String collectionId : collectionIds) {
        Collection collection = getCollection(gpkg, serviceId, collectionId);

        collections.addCollection(collection);
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
    return CollectionUtils.createCollection(gpkg.getFeatureDao(collectionId));
  }

  /**
   * Return all items of a collection from a given service.
   *
   * @param serviceId
   * @param collectionId
   * @return
   */
  public FeatureCollection getItems(String serviceId, String collectionId, FeatureQuery query) {
    FeatureCollection featureCollection = new FeatureCollection();

    try (GeoPackage gpkg = open(serviceId)) {
      FeatureDao featureDao = gpkg.getFeatureDao(collectionId);

      String orderBy =  featureDao.getIdColumnName();
      Integer limit = query.getLimit();
      Long offset = query.getOffset();

      Integer numberMatched = featureDao.count();
      featureCollection.setNumberMatched(numberMatched.longValue());

      FeatureResultSet featureResultSet = featureDao.queryForChunk(orderBy, limit, offset);

      try {
        while (featureResultSet.moveToNext()) {
          FeatureRow featureRow = featureResultSet.getRow();

          Feature feature = FeatureUtils.createFeature(featureRow);

          if (feature != null) {
            featureCollection.addFeature(feature);
          }
        }
      } finally {
        featureResultSet.close();
      }
    }

    return featureCollection;
  }

  public Feature getItem(String serviceId, String collectionId, Long featureId) {
    try (GeoPackage gpkg = open(serviceId)) {
      FeatureDao featureDao = gpkg.getFeatureDao(collectionId);

      FeatureResultSet featureResultSet = featureDao.queryForId(featureId);

      try {
        while (featureResultSet.moveToNext()) {
          FeatureRow featureRow = featureResultSet.getRow();

          return FeatureUtils.createFeature(featureRow);
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
}
