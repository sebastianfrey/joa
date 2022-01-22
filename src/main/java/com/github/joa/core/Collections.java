package com.github.joa.core;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

public class Collections {
  private List<Collection> collections = new ArrayList<>();
  @InjectLinks({
    @InjectLink(value = "/{serviceId}/collections", rel = "self", type = MediaType.APPLICATION_JSON, style = InjectLink.Style.ABSOLUTE)
  })
  @XmlJavaTypeAdapter(LinkAdapter.class)
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
