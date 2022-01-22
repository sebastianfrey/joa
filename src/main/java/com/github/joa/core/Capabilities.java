package com.github.joa.core;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

public class Capabilities {
  private String title;
  private String description;

  @InjectLinks({
      @InjectLink(value = "/{serviceId}", rel = "self", type = MediaType.APPLICATION_JSON, style = InjectLink.Style.ABSOLUTE),
      @InjectLink(value = "/{serviceId}/conformance", rel = "conformance", type = MediaType.APPLICATION_JSON, style = InjectLink.Style.ABSOLUTE),
      @InjectLink(value = "/{serviceId}/collections", rel = "data", type = MediaType.APPLICATION_JSON, style = InjectLink.Style.ABSOLUTE)
  })
  @XmlJavaTypeAdapter(LinkAdapter.class)
  private List<Link> links = new ArrayList<>();

  public Capabilities(String title) {
    this(title, "");
  }

  public Capabilities(String title, String description) {
    this.title = title;
    this.description = description;
    /*
     * this.links.add(Link.builder()
     * .withRel("conformance")
     * .withTitle("Implementierte OGC-API-Konformitätsklassen")
     * .withHref("http://localhost:8080/api/" + title + "/conformance")
     * .build());
     *
     * this.links.add(Link.builder()
     * .withRel("data")
     * .withTitle("Daten")
     * .withHref("http://localhost:8080/api/" + title + "/collections")
     * .build());
     */
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

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

}
