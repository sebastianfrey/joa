package com.github.sebastianfrey.joa.models;

/**
 * Extensions for jaxrs MediaTypes.
 */
public class MediaType extends javax.ws.rs.core.MediaType {
  /**
   * The GeoJSON MediaType.
   */
  public static final String APPLICATION_GEO_JSON = "application/geo+json";

  /**
   * The OpenAPI-YAML MediaType.
   */
  public static final String APPLICATION_OPENAPI_YAML = "application/vnd.oai.openapi;version=3.0";

  /**
   * The OpenAPI-JSON MediaType.
   */
  public static final String APPLICATION_OPENAPI_JSON = "application/vnd.oai.openapi+json;version=3.0";
}
