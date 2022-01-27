package com.github.sebastianfrey.joa.models;

/**
 * The Extent model represents the OGC API Extent type.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/extent.yaml"
 */
public class Extent {
  private Spatial spatial = new Spatial();
  private Temporal temporal = new Temporal();

  /**
   * returns the spatial property from an Extent instance.
   *
   * @return The supported spatial extents.
   */
  public Spatial getSpatial() {
    return spatial;
  }

  public void setSpatial(Spatial spatial) {
    this.spatial = spatial;
  }

  public Extent spatial(Spatial spatial) {
    setSpatial(spatial);
    return this;
  }

  /**
   * returns the temporal property from an Extent instance.
   *
   * @return The supported temporal extents.
   */
  public Temporal getTemporal() {
    return temporal;
  }

  public void setTemporal(Temporal temporal) {
    this.temporal = temporal;
  }

  public Extent temporal(Temporal temporal) {
    setTemporal(temporal);
    return this;
  }
}
