package com.github.sebastianfrey.joa.models;

import java.util.List;

/**
 * The ItemsQuery model represents the OGC API parameters list for an item query.
 */
public abstract class ItemQuery {
  public static List<String> RESERVED_QUERY_PARAMS = List.of("f", "crs");

  /**
   * The optional crs parameter, which allows to specify the output CRS for returned geometries.
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part2/1.0/openapi/parameters/crs.yaml"
   *
   * @return The crs.
   */
  public abstract Crs getCrs();
}
