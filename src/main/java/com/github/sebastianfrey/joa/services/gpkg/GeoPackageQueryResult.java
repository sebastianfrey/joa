package com.github.sebastianfrey.joa.services.gpkg;

import mil.nga.geopackage.features.user.FeaturePaginatedResults;

public class GeoPackageQueryResult {
  private Long count;
  private FeaturePaginatedResults paginatedResults;

  public GeoPackageQueryResult(Long count, FeaturePaginatedResults paginatedResults) {
    this.count = count;
    this.paginatedResults = paginatedResults;
  }

  public Long getCount() {
    return count;
  }

  public FeaturePaginatedResults getPaginatedResults() {
    return paginatedResults;
  }
}
