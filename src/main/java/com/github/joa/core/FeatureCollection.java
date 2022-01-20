package com.github.joa.core;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import mil.nga.sf.geojson.Feature;

public class FeatureCollection {
  private List<Link> links = new ArrayList<>();
  private List<Feature> features = new ArrayList<>();
  private Long numberMatched;

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
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

  public Long getNumberMatched() {
    return numberMatched;
  }

  public void setNumberMatched(Long numberMatched) {
    this.numberMatched = numberMatched;
  }

  public String getType() {
    return "FeatureCollection";
  }

  public String getTimeStamp() {
    return Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
  }

  public Integer getNumberReturned() {
    return features.size();
  }
}
