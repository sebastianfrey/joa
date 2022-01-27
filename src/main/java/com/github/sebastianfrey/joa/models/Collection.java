package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Collection model represents the OGC API Collection type.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/collection.yaml"
 */
@JsonPropertyOrder({"id", "title", "description", "itemType", "crs", "links"})
@JsonIgnoreProperties({"serviceId", "collectionId"})
public class Collection extends Linkable {
  private String serviceId = "";
  private String collectionId = "";
  private String title = "";
  private String description = "";
  private Extent extent = new Extent();
  private String itemType;
  private List<String> crs = new ArrayList<>();

  /**
   * returns the id property from a Collection instance
   *
   * @return String id
   */
  public String getId() {
    return getCollectionId();
  }

  /**
   * returns the serviceId property from a Collection instance
   *
   * @return String serviceId
   */
  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Collection serviceId(String serviceId) {
    setServiceId(serviceId);
    return this;
  }

  /**
   * returns the collectionId property from a Collection instance
   *
   * @return String collectionId
   */
  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public Collection collectionId(String collectionId) {
    setCollectionId(collectionId);
    return this;
  }

  /**
   * returns the title property from a Collection instance
   *
   * @return String title
   */
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Collection title(String title) {
    setTitle(title);
    return this;
  }

  /**
   * returns the description property from a Collection instance
   *
   * @return String description
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Collection description(String description) {
    setDescription(description);
    return this;
  }

  /**
   * returns the extent property from a Collection instance
   *
   * @return Collection Boundingbox
   */
  public Extent getExtent() {
    return extent;
  }

  public void setExtent(Extent extent) {
    this.extent = extent;
  }

  public Collection extent(Extent extent) {
    setExtent(extent);
    return this;
  }

  /**
   * returns the itemType property from a Collection instance
   *
   * @return String itemType
   */
  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public Collection itemType(String itemType) {
    setItemType(itemType);
    return this;
  }

  /**
   * returns the crs property from a Collection instance
   *
   * @return List of supported crs
   */
  public List<String> getCrs() {
    return crs;
  }

  public void setCrs(List<String> crs) {
    this.crs = crs;
  }

  public void addCrs(String crs) {
    this.crs.add(crs);
  }

  public Collection crs(List<String> crs) {
    setCrs(crs);
    return this;
  }

  public Collection crs(String crs) {
    this.crs.add(crs);
    return this;
  }

  public Collection bbox(Bbox bbox) {
    getExtent().getSpatial().addBbox(bbox);
    return this;
  }

  public Collection temporal(List<String> interval) {
    getExtent().getTemporal().addInterval(interval);
    return this;
  }
}
