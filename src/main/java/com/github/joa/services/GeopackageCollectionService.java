package com.github.joa.services;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.NotFoundException;

import com.github.joa.api.Capabilities;
import com.github.joa.api.Collection;
import com.github.joa.api.Collections;
import com.github.joa.api.Conformance;
import com.github.joa.api.FeatureCollection;
import com.github.joa.api.FeatureQuery;
import com.github.joa.db.GeoPackageService;
import com.github.joa.util.CollectionUtils;

import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.GeoPackageException;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureResultSet;
import mil.nga.geopackage.features.user.FeatureRow;
import mil.nga.sf.geojson.Feature;

public class GeopackageCollectionService implements CollectionService {

  private GeoPackageService geopackageService;

  public GeopackageCollectionService(GeoPackageService geopackageService) {
    this.geopackageService = geopackageService;
  }

  public Capabilities getCapabilities(String serviceId) {
    if (!geopackageService.exists(serviceId)) {
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

    try (GeoPackage gpkg = geopackageService.open(serviceId)) {

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
    try (GeoPackage gpkg = geopackageService.open(serviceId)) {
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
   * Return all items of
   *
   * @param serviceId
   * @param collectionId
   * @return
   */
  public FeatureCollection getItems(String serviceId, String collectionId, FeatureQuery query) {
    FeatureCollection featureCollection = new FeatureCollection();

    try (GeoPackage gpkg = geopackageService.open(serviceId)) {
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

          Feature feature = CollectionUtils.createFeature(featureRow);

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
    try (GeoPackage gpkg = geopackageService.open(serviceId)) {
      FeatureDao featureDao = gpkg.getFeatureDao(collectionId);

      FeatureResultSet featureResultSet = featureDao.queryForId(featureId);

      try {
        while (featureResultSet.moveToNext()) {
          FeatureRow featureRow = featureResultSet.getRow();

          return CollectionUtils.createFeature(featureRow);
        }
      } finally {
        featureResultSet.close();
      }
    }

    throw new NotFoundException("Feature with ID '" + featureId + "' does not exist.");
  }
}
