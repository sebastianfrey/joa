package com.github.sebastianfrey.joa.models;

import javax.ws.rs.core.MultivaluedMap;

/**
 * The FeatureQuery model represents the OGC API parameters list for an items query.
 */
public abstract class FeatureQuery {
  /**
   * returns the raw query string from an items query.
   *
   * @return The raw query string
   */
  public abstract String getQueryString();

  /**
   * The optional bbox parameter, which allows to query by a spatial extent.
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/bbox.yaml"
   *
   * @return The spatial extent.
   */
  public abstract Bbox getBbox();

  /**
   * The optional datetime parameter, which allows to query by a temporal extent.
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/datetime.yaml"
   *
   * @return The temporal extent.
   */
  public abstract Datetime getDatetime();

  /**
   * The optional offset parameter to skip a specific number of items.
   *
   * @return The number of items to skip.
   */
  public abstract Long getOffset();

  /**
   * The optional limit prameter to limit the number of items to return.
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/limit.yaml"
   *
   * @return The number of items to return.
   */
  public abstract Integer getLimit();

  /**
   * returns the list of all remainig query parameters from an items query.
   *
   * @return Map of query parameters
   */
  public abstract MultivaluedMap<String, String> getQueryParameters();
}
