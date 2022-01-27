package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Spatial model represents the OGC API Spatial Extent type.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/extent.yaml"
 */
public class Spatial {
  private List<List<Double>> bbox = new ArrayList<>();

  /**
   * returns the bbox property from a Spatial instance.
   *
   * @return List of spatial extents.
   */
  public List<List<Double>> getBbox() {
    return bbox;
  }

  public void setBbox(List<List<Double>> bbox) {
    this.bbox = bbox;
  }

  public void addBbox(List<Double> bbox) {
    this.bbox.add(bbox);
  }

  public void addBbox(Bbox bbox) {
    addBbox(List.of(bbox.getMinX(), bbox.getMinY(), bbox.getMaxX(), bbox.getMaxY()));
  }

  public Spatial bbox(Bbox bbox) {
    addBbox(bbox);
    return this;
  }
}
