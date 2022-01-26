package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;
import mil.nga.geopackage.BoundingBox;
import mil.nga.sf.GeometryEnvelope;

public class Spatial {
  private List<List<Double>> bbox = new ArrayList<>();

  public List<List<Double>> getBbox() {
    return bbox;
  }

  public void setBbox(List<List<Double>> bbox) {
    this.bbox = bbox;
  }

  public void addBbox(List<Double> bbox) {
    this.bbox.add(bbox);
  }

  public void addBbox(GeometryEnvelope bbox) {
    addBbox(List.of(bbox.getMinX(), bbox.getMinY(), bbox.getMaxX(), bbox.getMaxY()));
  }

  public void addBbox(BoundingBox bbox) {
    addBbox(bbox.buildEnvelope());
  }
}