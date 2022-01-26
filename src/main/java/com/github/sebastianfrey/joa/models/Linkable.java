package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;
import org.glassfish.jersey.linking.InjectLinks;

public class Linkable {
  @InjectLinks
  private List<Link> links = new ArrayList<>();

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public void addLink(Link link) {
    this.links.add(link);
  }
}
