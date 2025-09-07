package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Collections model represents the OGC API Collections type.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/collections.yaml"
 */
@JsonPropertyOrder({"title", "description", "collections", "links"})
@JsonIgnoreProperties({"serviceId"})
public class Collections extends Linkable implements Iterable<Collection> {
  private String serviceId = "";
  private String title = "";
  private String description = "";
  private List<Collection> collections = new ArrayList<>();
  private Set<String> crs = new HashSet<>();

  /**
   * returns the serviceId property from a Collections instance
   *
   * @return String serviceId
   */
  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Collections serviceId(String serviceId) {
    setServiceId(serviceId);
    return this;
  }

  /**
   * returns the title property from a Collections instance
   *
   * @return String title
   */
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Collections title(String title) {
    setTitle(title);
    return this;
  }

  /**
   * returns the description property from a Collections instance
   *
   * @return String description
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Collections description(String description) {
    setDescription(description);
    return this;
  }

  /**
   * returns the collections property from a Collections instance
   *
   * @return List of collections
   */
  public List<Collection> getCollections() {
    return collections;
  }

  public void setCollections(List<Collection> collections) {
    this.collections = collections;
  }

  public Collections collections(List<Collection> collections) {
    setCollections(collections);
    return this;
  }

  public void addCollection(Collection collection) {
    this.collections.add(collection);
  }

  public Collections collection(Collection collection) {
    addCollection(collection);
    return this;
  }


  /**
   * returns the crs property from a Collections instance
   *
   * @return List of crs
   */
  public Set<String> getCrs() {
    return crs;
  }

  public void setCrs(Set<String> crs) {
    this.crs = crs;
  }

  public Collections crs(Set<String> crs) {
    setCrs(crs);
    return this;
  }

  @Override
  public Iterator<Collection> iterator() {
   return collections.iterator();
  }
}
