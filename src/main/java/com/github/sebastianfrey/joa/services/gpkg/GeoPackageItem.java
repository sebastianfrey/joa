package com.github.sebastianfrey.joa.services.gpkg;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.sebastianfrey.joa.models.Item;
import mil.nga.sf.geojson.Geometry;
import mil.nga.sf.geojson.Feature;

/**
 * GeoPackage specific implementation of the Item model.
 *
 * @author sfrey
 */
public class GeoPackageItem extends Item<Geometry, GeoPackageItem> {
  @JsonIgnore
  private Feature feature;

  public Feature getFeature() {
    return feature;
  }

  public void setFeature(Feature feature) {
    this.feature = feature;
  }

  public GeoPackageItem feature(Feature feature) {
    setFeature(feature);
    return this;
  }

  @Override
  public String getId() {
    return feature.getId();
  }

  @Override
  public double[] getBbox() {
    return feature.getBbox();
  }

  @Override
  public Map<String, Object> getProperties() {
    return feature.getProperties();
  }

  @Override
  public Geometry getGeometry() {
    return feature.getGeometry();
  }
}
