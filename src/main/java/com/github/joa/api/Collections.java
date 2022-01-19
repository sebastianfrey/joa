package com.github.joa.api;

import java.util.ArrayList;
import java.util.List;

public class Collections {
  private List<Collection> collections = new ArrayList<>();
  private List<Link> links = new ArrayList<>();
  private String title = "";
  private String description = "";

  public Collections(String title) {
    this(new ArrayList<>(), title, "");
  }

  public Collections(List<Collection> collections) {
    this(collections, "", "");
  }

  public Collections(List<Collection> collections, String title) {
    this(collections, title, "");
  }

  public Collections(List<Collection> collections, String title, String description) {
    this.collections = collections;
    this.title = title;
    this.description = description;
  }

  public List<Collection> getCollections() {
    return collections;
  }

  public void setCollections(List<Collection> collections) {
    this.collections = collections;
  }

  public void addCollection(Collection collection) {
    this.collections.add(collection);
  }

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
