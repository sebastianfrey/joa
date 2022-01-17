package com.github.geoio.api;

import java.util.ArrayList;
import java.util.List;

import mil.nga.sf.geojson.Feature;

public class FeatureCollection {
  List<Link> links = new ArrayList<>();

  List<Feature> features = new ArrayList<>();

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
}
