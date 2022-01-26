package com.github.sebastianfrey.joa.models;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "type", "bbox", "id", "geometry", "properties", "links"})
@JsonIgnoreProperties({ "serviceId", "collectionId" })
@JsonInclude(Include.NON_NULL)
public abstract class Item<Geometry> extends Linkable {
  @JsonIgnore
  private String serviceId = "";
  @JsonIgnore
  private String collectionId = "";

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

  public abstract String getId();

  public abstract double[] getBbox();

  public abstract Map<String, Object> getProperties();

  public abstract String getType();

  public abstract Geometry getGeometry();
}
