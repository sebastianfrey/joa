package com.github.joa.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.joa.util.LinkUtils;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

public class Services {
  List<String> services;

  @InjectLinks({
    @InjectLink(value = "/", rel = "self", type = MediaType.APPLICATION_JSON, style = InjectLink.Style.ABSOLUTE)
  })
  @XmlJavaTypeAdapter(LinkAdapter.class)
  List<Link> links = new ArrayList<>();

  public Services() {
  }

  public Services(Set<String> services) {
    this(new ArrayList<>(services));
  }

  public Services(List<String> services) {
    this.services = services;
  }

  public List<String> getServices() {
    return services;
  }

  public void setServices(List<String> services) {
    this.services = services;
  }

  public List<Link> getLinks() {
    for (String service : services) {
      String path = LinkUtils.makeAbsolute("/api/" + service, links);


      addLink(Link.fromPath(path)
        .rel("service")
        .type(MediaType.APPLICATION_JSON)
        .build());
    }

    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public void addLink(Link link) {
    this.links.add(link);
  }
}
