package com.github.sebastianfrey.joa.core;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"title", "description", "collections", "links"})
public class Collections extends Linkable {
  private List<Collection> collections = new ArrayList<>();

  private String serviceId;
  private String description = "";

  public Collections(String serviceId) {
    this(new ArrayList<>(), serviceId, "");
  }

  public Collections(List<Collection> collections) {
    this(collections, "", "");
  }

  public Collections(List<Collection> collections, String serviceId) {
    this(collections, serviceId, "");
  }

  public Collections(List<Collection> collections, String serviceId, String description) {
    this.collections = collections;
    this.serviceId = serviceId;
    this.description = description;
  }



  public List<Collection> getCollections() {
    return collections;
  }

  public void setCollections(List<Collection> collections) {
    this.collections = collections;
  }

  public void addCollection(Collection collection) {
    this.collections.add(collection);
  }

  @JsonProperty("title")
  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
