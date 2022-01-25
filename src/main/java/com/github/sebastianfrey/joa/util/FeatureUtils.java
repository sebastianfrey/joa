package com.github.sebastianfrey.joa.util;

import java.util.HashMap;
import java.util.Map;

import mil.nga.geopackage.features.user.FeatureRow;
import mil.nga.geopackage.geom.GeoPackageGeometryData;
import mil.nga.geopackage.user.ColumnValue;
import mil.nga.sf.Geometry;
import mil.nga.sf.geojson.Feature;
import mil.nga.sf.geojson.FeatureConverter;

public class FeatureUtils {
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
}
