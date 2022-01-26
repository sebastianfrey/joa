package com.github.sebastianfrey.joa.models;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"type", "numberReturned", "numberMatched", "timeStamp", "features", "links"})
@JsonIgnoreProperties({"serviceId", "collectionId", "nextPageAvailable", "prevPageAvailable",
    "firstPageAvailable", "lastPageAvailable"})
public abstract class Items<Feature> extends Linkable {
  @JsonIgnore
  private String serviceId = "";
  @JsonIgnore
  private String collectionId = "";

  private List<Feature> features = new ArrayList<>();

  public String getType() {
    return "FeatureCollection";
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

  public List<Feature> getFeatures() {
    return features;
  }

  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

  public void addFeature(Feature feature) {
    this.features.add(feature);
  }

  public String getTimeStamp() {
    return Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
  }

  public Integer getNumberReturned() {
    return features.size();
  }

  @JsonIgnore
  public abstract boolean isNextPageAvailable();

  @JsonIgnore
  public abstract boolean isPrevPageAvailable();

  @JsonIgnore
  public abstract boolean isFirstPageAvailable();

  @JsonIgnore
  public abstract boolean isLastPageAvailable();

  public abstract Long getNumberMatched();

  public abstract void setNumberMatched(Long total);
}
