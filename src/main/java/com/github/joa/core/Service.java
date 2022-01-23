package com.github.joa.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"title", "description", "links"})
public class Service extends Linkable {
  @JsonProperty("title")
  private String serviceId;
  private String description;

  public Service(String serviceId) {
    this(serviceId, "");
  }

  public Service(String serviceId, String description) {
    this.serviceId = serviceId;
    this.description = description;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String title) {
    this.serviceId = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
