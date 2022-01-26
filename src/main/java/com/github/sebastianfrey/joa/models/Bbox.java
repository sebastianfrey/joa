package com.github.sebastianfrey.joa.models;

public class Bbox {
  private Double minX;
  private Double minY;
  private Double minZ;
  private Double maxX;
  private Double maxY;
  private Double maxZ;

  public Bbox() {}

  public Bbox minX(Double minX) {
    this.minX = minX;
    return this;
  }

  public Bbox minY(Double minY) {
    this.minY = minY;
    return this;
  }

  public Bbox minZ(Double minZ) {
    this.minZ = minZ;
    return this;
  }

  public Bbox maxX(Double maxX) {
    this.maxX = maxX;
    return this;
  }

  public Bbox maxY(Double maxY) {
    this.maxY = maxY;
    return this;
  }

  public Bbox maxZ(Double maxZ) {
    this.maxZ = maxZ;
    return this;
  }

  public void setMinX(Double minX) {
    this.minX = minX;
  }

  public void setMinY(Double minY) {
    this.minY = minY;
  }

  public void setMinZ(Double minZ) {
    this.minZ = minZ;
  }

  public void setMaxX(Double maxX) {
    this.maxX = maxX;
  }

  public void setMaxY(Double maxY) {
    this.maxY = maxY;
  }

  public void setMaxZ(Double maxZ) {
    this.maxZ = maxZ;
  }

  public Double getMaxX() {
    return maxX;
  }

  public Double getMaxY() {
    return maxY;
  }

  public Double getMaxZ() {
    return maxZ;
  }

  public Double getMinX() {
    return minX;
  }

  public Double getMinY() {
    return minY;
  }

  public Double getMinZ() {
    return minZ;
  }
}
