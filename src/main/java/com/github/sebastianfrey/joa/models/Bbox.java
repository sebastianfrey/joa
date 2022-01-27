package com.github.sebastianfrey.joa.models;

/**
 * The Bbox model represents the OGC API bbox query parameter.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/bbox.yaml"
 */
public class Bbox {
  private Double minX;
  private Double minY;
  private Double minZ;
  private Double maxX;
  private Double maxY;
  private Double maxZ;

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
