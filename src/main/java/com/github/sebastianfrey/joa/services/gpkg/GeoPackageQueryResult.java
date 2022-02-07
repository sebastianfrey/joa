package com.github.sebastianfrey.joa.services.gpkg;

import mil.nga.geopackage.features.user.FeatureResultSet;

public class GeoPackageQueryResult {
  private Long count;
  private FeatureResultSet featureResultSet;

  public GeoPackageQueryResult(Long count, FeatureResultSet featureResultSet) {
    this.count = count;
    this.featureResultSet = featureResultSet;
  }

  public Long getCount() {
    return count;
  }

  public FeatureResultSet getFeatureResultSet() {
    return featureResultSet;
  }
}
