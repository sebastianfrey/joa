package com.github.joa.core;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

public class Collection {
  @InjectLinks({
      @InjectLink(value = "/{serviceId}/collections/${instance.id}/items", rel = "items", type = MediaTypeExt.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE)
  })
  @XmlJavaTypeAdapter(LinkAdapter.class)
  private List<Link> links = new ArrayList<>();
  private String id = "";
  private String title = "";
  private String description = "";
  private Extent extent = new Extent();
  private String itemType;
  private List<String> crs;

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public void addLink(Link link) {
    this.links.add(link);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Extent getExtent() {
    return extent;
  }

  public void setExtent(Extent extent) {
    this.extent = extent;
  }

  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public List<String> getCrs() {
    return crs;
  }

  public void setCrs(List<String> crs) {
    this.crs = crs;
  }
}
