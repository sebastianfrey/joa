package com.github.sebastianfrey.joa.models;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Item model represents the OGC API Feature type.
 *
 * @param <G> The geometry type used by an implementer. Must be serializable to valid GeoJSON
 *        geometry.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/featureGeoJSON.yaml"
 */
@JsonPropertyOrder({"type", "bbox", "id", "geometry", "properties", "links"})
@JsonIgnoreProperties({"serviceId", "collectionId"})
@JsonInclude(Include.NON_NULL)
public abstract class Item<G, T extends Item<G, T>> extends Linkable {
  @JsonIgnore
  private String serviceId = "";
  @JsonIgnore
  private String collectionId = "";

  /**
   * returns the type property from a Item instance
   *
   * @return String type
   */
  public String getType() {
    return "Feature";
  }

  /**
   * returns the serviceId property from a Item instance
   *
   * @return String serviceId
   */
  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  @SuppressWarnings("unchecked")
  public T serviceId(String serviceId) {
    setServiceId(serviceId);
    return (T) this;
  }

  /**
   * returns the collectionId property from a Item instance
   *
   * @return String collectionId
   */
  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  @SuppressWarnings("unchecked")
  public T collectionId(String collectionId) {
    setCollectionId(collectionId);
    return (T) this;
  }

  /**
   * returns the id property from a Item instance
   *
   * @return String id
   */
  public abstract String getId();

  /**
   * returns the bbox property from a Item instance
   *
   * @return Bbox Array.
   */
  public abstract double[] getBbox();

  /**
   * returns the properties property from a Item instance
   *
   * @return Map of properties
   */
  public abstract Map<String, Object> getProperties();

  /**
   * returns the geometry property from a Item instance
   *
   * @return Geometry instance
   */
  public abstract G getGeometry();
}
