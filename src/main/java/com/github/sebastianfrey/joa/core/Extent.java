package com.github.sebastianfrey.joa.core;


public class Extent {
  private Spatial spatial = new Spatial();

  private Temporal temporal = new Temporal();

  public Spatial getSpatial() {
    return spatial;
  }

  public void setSpatial(Spatial spatial) {
    this.spatial = spatial;
  }

  public Temporal getTemporal() {
    return temporal;
  }

  public void setTemporal(Temporal temporal) {
    this.temporal = temporal;
  }
}
