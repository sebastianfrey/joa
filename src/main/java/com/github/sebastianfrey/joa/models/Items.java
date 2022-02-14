package com.github.sebastianfrey.joa.models;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import mil.nga.sf.geojson.Feature;

/**
 * The Items model represents the OGC API FeatureCollection type.
 *
 * @param <F> The feature type used by an implementer. Must be serializable to valid GeoJSON
 *        feature.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/featureCollectionGeoJSON.yaml"
 */
@JsonPropertyOrder({"type", "numberReturned", "numberMatched", "timeStamp", "features", "links"})
@JsonIgnoreProperties({"serviceId", "collectionId", "nextPageAvailable", "prevPageAvailable",
    "firstPageAvailable", "lastPageAvailable"})
public abstract class Items<T extends Items<T>> extends Linkable implements Iterable<Feature> {
  @JsonIgnore
  private String serviceId = "";
  @JsonIgnore
  private String collectionId = "";

  private Long numberMatched;
  private List<Feature> features = new ArrayList<>();

  /**
   * returns the type property from a Items instance
   *
   * @return String type
   */
  public String getType() {
    return "FeatureCollection";
  }

  /**
   * returns the serviceId property from a Items instance
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
   * returns the collectionId property from a Items instance
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
   * returns the features property from a Items instance
   *
   * @return List of features
   */
  public List<Feature> getFeatures() {
    return features;
  }

  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

  @SuppressWarnings("unchecked")
  public T features(List<Feature> features) {
    setFeatures(features);
    return (T) this;
  }

  public void addFeature(Feature feature) {
    this.features.add(feature);
  }

  @SuppressWarnings("unchecked")
  public T feature(Feature feature) {
    this.addFeature(feature);
    return (T) this;
  }

  /**
   * returns the timeStamp property from a Items instance
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/timeStamp.yaml"
   * @return Timestamp which adheres to RFC 3339
   */
  public String getTimeStamp() {
    return Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
  }

  /**
   * returns the numberReturned property from a Items instance
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/numberReturned.yaml"
   * @return Count of returned features as Integer
   */
  public Integer getNumberReturned() {
    return features.size();
  }

  /**
   * returns the numberMatched property from a Items instance
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/numberMatched.yaml"
   * @return Number of total matched features as Long
   */
  public Long getNumberMatched() {
    return numberMatched;
  }

  public void setNumberMatched(Long numberMatched) {
    this.numberMatched = numberMatched;
  }

  @SuppressWarnings("unchecked")
  public T numberMatched(Long numberMatched) {
    setNumberMatched(numberMatched);
    return (T) this;
  }



  @Override
  public Iterator<Feature> iterator() {
    return features.iterator();
  }

  /**
   * returns the nextPageAvailable property from a Items instance
   *
   * @return Indicates if the next page is available.
   */
  @JsonIgnore
  public abstract boolean isNextPageAvailable();

  /**
   * returns the nextPageAvailable property from a Items instance
   *
   * @return Indicates if the previous page is available.
   */
  @JsonIgnore
  public abstract boolean isPrevPageAvailable();

  /**
   * returns the nextPageAvailable property from a Items instance
   *
   * @return Indicates if the first page is available.
   */
  @JsonIgnore
  public abstract boolean isFirstPageAvailable();

  /**
   * returns the nextPageAvailable property from a Items instance
   *
   * @return Indicates if the last page is available.
   */
  @JsonIgnore
  public abstract boolean isLastPageAvailable();
}
