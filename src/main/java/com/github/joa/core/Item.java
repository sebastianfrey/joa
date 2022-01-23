package com.github.joa.core;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import mil.nga.sf.geojson.Feature;
import mil.nga.sf.geojson.Geometry;

@JsonPropertyOrder({ "type", "bbox", "id", "geometry", "properties", "links"})
@JsonInclude(Include.NON_NULL)
public class Item extends Linkable {
  @JsonIgnore
  private Feature feature;
  @JsonIgnore
  private String serviceId = "";
  @JsonIgnore
  private String collectionId = "";

  public Item(Feature feature) {
    this.feature = feature;
  }

  public Feature getFeature() {
    return feature;
  }

  public void setFeature(Feature feature) {
    this.feature = feature;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public String getId() {
    return feature.getId();
  }

  public Geometry getGeometry() {
    return feature.getGeometry();
  }

  public double[] getBbox() {
    return feature.getBbox();
  }

  public Map<String, Object> getProperties() {
    return feature.getProperties();
  }

  public String getType() {
    return feature.getType();
  }
}
