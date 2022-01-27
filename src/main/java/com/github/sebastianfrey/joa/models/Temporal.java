package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Temporal model represents the OGC API Temporal Extent type.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/extent.yaml"
 */
public class Temporal {
  private List<List<String>> interval = new ArrayList<>();

    /**
   * returns the interval property from a Temporal instance.
   *
   * @return List of temporal extents.
   */
  public List<List<String>> getInterval() {
    return interval;
  }

  public void setInterval(List<List<String>> interval) {
    this.interval = interval;
  }

  public void addInterval(List<String> interval) {
    this.interval.add(interval);
  }

  public Temporal interval(List<String> interval) {
    addInterval(interval);
    return this;
  }
}
