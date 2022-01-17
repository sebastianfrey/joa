package com.github.geoio.service;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.geoio.api.Collection;
import com.github.geoio.api.FeatureCollection;
import com.github.geoio.db.GeopackageDatabase;

import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.GeoPackageManager;
import mil.nga.geopackage.contents.Contents;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureResultSet;
import mil.nga.geopackage.features.user.FeatureRow;
import mil.nga.geopackage.features.user.FeatureTable;
import mil.nga.geopackage.geom.GeoPackageGeometryData;
import mil.nga.geopackage.user.ColumnValue;
import mil.nga.oapi.features.json.FeaturesConverter;
import mil.nga.sf.Geometry;
import mil.nga.sf.geojson.Feature;
import mil.nga.sf.geojson.FeatureConverter;

public class CollectionService {

  private GeopackageDatabase database;

  public CollectionService(GeopackageDatabase database) {
    this.database = database;
  }

  public List<Collection> getCollections() {
    List<Collection> collections = new ArrayList<>();

    try (GeoPackage gpkg = database.open()) {

      List<String> featureTables = gpkg.getFeatureTables();

      for (String featureTable : featureTables) {
        FeatureTable table = gpkg.getFeatureDao(featureTable).getTable();

        collections.add(createCollection(table.getContents()));
      }
    }

    return collections;
  }

  public Collection getCollection(String id) throws IOException {
    try (GeoPackage gpkg = database.open()) {
      FeatureTable table = gpkg.getFeatureDao(id).getTable();

      return createCollection(table.getContents());
    }
  }

  public FeatureCollection getItems(String id) {
    FeatureCollection featureCollection = new FeatureCollection();

    try (GeoPackage gpkg = database.open()) {
      FeatureDao featureDao = gpkg.getFeatureDao(id);
      String idColumn = featureDao.getIdColumnName();

      FeatureResultSet featureResultSet = featureDao.queryForChunk(idColumn + " ASC", 10, 0);

      try {
        while (featureResultSet.moveToNext()) {
          FeatureRow featureRow = featureResultSet.getRow();

          Feature feature = createFeature(featureRow);

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

  private Feature createFeature(FeatureRow featureRow) {
    Feature feature = null;

    GeoPackageGeometryData geometryData = featureRow.getGeometry();
    if (geometryData != null && !geometryData.isEmpty()) {
      Geometry geometry = geometryData.getGeometry();

      feature = FeatureConverter.toFeature(geometry);

      feature.setProperties(new HashMap<>());

      for (Map.Entry<String, ColumnValue> entry : featureRow.getAsMap()) {
        ColumnValue value = entry.getValue();
        String key = entry.getKey();
        if (!key.equals(featureRow.getGeometryColumnName())) {
          feature.getProperties().put(key, value.getValue());
        }
      }
    }

    return feature;
  }

  private Collection createCollection(Contents contents) {
    Collection collection = new Collection();

    String id = contents.getTableName();
    String identifier = contents.getIdentifier();
    String description = contents.getDescription();
    String crs = "http://www.opengis.net/def/crs/EPSG/0/" + contents.getSrsId();

    collection.setId(id);
    collection.setDescription(description);
    collection.setTitle(identifier);
    collection.setCrs(List.of(crs));
    collection.setItemType("feature");

    return collection;
  }
}
