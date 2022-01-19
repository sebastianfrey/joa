package com.github.joa.api;

import java.util.ArrayList;
import java.util.List;

public class Capabilities {
  private String title;
  private String description;
  private List<Link> links = new ArrayList<>();

  public Capabilities(String title) {
    this(title, "");
  }

  public Capabilities(String title, String description) {
    this.title = title;
    this.description = description;
    this.links.add(Link.builder()
        .withRel("conformance")
        .withTitle("Implementierte OGC-API-Konformitätsklassen")
        .withHref("http://localhost:8080/api/" + title + "/conformance")
        .build());

    this.links.add(Link.builder()
      .withRel("data")
      .withTitle("Daten")
      .withHref("http://localhost:8080/api/" + title + "/collections")
      .build());
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
