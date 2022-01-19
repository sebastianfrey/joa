package com.github.joa.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.joa.api.Collection;
import com.github.joa.api.Link;

import mil.nga.geopackage.contents.Contents;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureRow;
import mil.nga.geopackage.geom.GeoPackageGeometryData;
import mil.nga.geopackage.user.ColumnValue;
import mil.nga.sf.Geometry;
import mil.nga.sf.geojson.Feature;
import mil.nga.sf.geojson.FeatureConverter;

public class CollectionUtils {
  public static Feature createFeature(FeatureRow featureRow) {
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

      feature.setId(String.valueOf(featureRow.getId()));
    }

    return feature;
  }

  public static Collection createCollection(FeatureDao featureDao) {
    Contents contents = featureDao.getContents();

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
    collection.getExtent().getSpatial().addBbox(contents.getBoundingBox());
    collection.addLink(Link.builder()
      .withRel("items")
      .withHref("http://localhost:8080/api/pot_WEG_Luebz_linie/collections/" + id + "/items").build());

    return collection;
  }
}
