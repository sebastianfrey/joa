package com.github.joa.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Collection extends Linkable {
  @JsonIgnore
  private String serviceId = "";
  @JsonProperty("id")
  private String collectionId = "";
  private String title = "";
  private String description = "";
  private Extent extent = new Extent();
  private String itemType;
  private List<String> crs;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Extent getExtent() {
    return extent;
  }

  public void setExtent(Extent extent) {
    this.extent = extent;
  }

  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public List<String> getCrs() {
    return crs;
  }

  public void setCrs(List<String> crs) {
    this.crs = crs;
  }
}
