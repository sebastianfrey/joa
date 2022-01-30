package com.github.sebastianfrey.joa.models;

import java.util.List;
import java.util.stream.Stream;

/**
 * The Bbox model represents the OGC API bbox query parameter.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/bbox.yaml"
 */
public class Bbox {
  public static final String SEPARATOR = ",";

  private List<Double> values;

  private Double minX;
  private Double minY;
  private Double minZ;
  private Double maxX;
  private Double maxY;
  private Double maxZ;

  public Bbox() {}

  public Bbox(String value) {
    parse(value);
  }

  private void parse(String input) {
    if (input == null) {
      return;
    }

    try {
       values = Stream.of(input.trim().split(SEPARATOR)).map((part) -> Double.valueOf(part)).toList();
    } catch (NumberFormatException e) {
      return;
    }

    Boolean hasZ = values.size() > 4;

    int i = 0;

    minX = values.get(i++);
    minY = values.get(i++);
    minZ = hasZ ? values.get(i++) : null;
    maxX = values.get(i++);
    maxY = values.get(i++);
    maxZ = hasZ ? values.get(i++) : null;
  }

  public Boolean validate() {
    if (values == null || values.isEmpty()) {
      return true;
    }

    if (values.size() == 4) {
      return true;
    }

    if (values.size() == 6) {
      return true;
    }

    return false;
  }

  /**
   * returns the minX property from a Bbox instance.
   *
   * @return Double minX.
   */
  public Double getMinX() {
    return minX;
  }

  public void setMinX(Double minX) {
    this.minX = minX;
  }

  public Bbox minX(Double minX) {
    this.minX = minX;
    return this;
  }

  /**
   * returns the minY property from a Bbox instance.
   *
   * @return Double minY.
   */
  public Double getMinY() {
    return minY;
  }

  public void setMinY(Double minY) {
    this.minY = minY;
  }

  public Bbox minY(Double minY) {
    this.minY = minY;
    return this;
  }

  /**
   * returns the minZ property from a Bbox instance.
   *
   * @return Double minZ.
   */
  public Double getMinZ() {
    return minZ;
  }

  public void setMinZ(Double minZ) {
    this.minZ = minZ;
  }


  public Bbox minZ(Double minZ) {
    this.minZ = minZ;
    return this;
  }

  /**
   * returns the maxX property from a Bbox instance.
   *
   * @return Double maxX.
   */
  public Double getMaxX() {
    return maxX;
  }

  public void setMaxX(Double maxX) {
    this.maxX = maxX;
  }

  public Bbox maxX(Double maxX) {
    this.maxX = maxX;
    return this;
  }

  /**
   * returns the maxY property from a Bbox instance.
   *
   * @return Double maxY.
   */
  public Double getMaxY() {
    return maxY;
  }

  public void setMaxY(Double maxY) {
    this.maxY = maxY;
  }

  public Bbox maxY(Double maxY) {
    this.maxY = maxY;
    return this;
  }

  /**
   * returns the maxZ property from a Bbox instance.
   *
   * @return Double maxZ.
   */
  public Double getMaxZ() {
    return maxZ;
  }

  public void setMaxZ(Double maxZ) {
    this.maxZ = maxZ;
  }

  public Bbox maxZ(Double maxZ) {
    this.maxZ = maxZ;
    return this;
  }
}
