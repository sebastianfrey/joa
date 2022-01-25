package com.github.sebastianfrey.joa.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.glassfish.jersey.linking.InjectLinks;

public class Linkable {

  public static class LinkAdapter extends XmlAdapter<LinkJaxb, Link> {

    public LinkAdapter() {}

    public Link unmarshal(LinkJaxb p1) {

      Link.Builder builder = Link.fromUri(p1.getUri());

      for (Map.Entry<QName, Object> entry : p1.getParams().entrySet()) {
        builder.param(entry.getKey().getLocalPart(), entry.getValue().toString());
      }
      return builder.build();
    }

    public LinkJaxb marshal(Link p1) {
      Map<QName, Object> params = new HashMap<>();

      for (Map.Entry<String, String> entry : p1.getParams().entrySet()) {
        params.put(new QName("", entry.getKey()), entry.getValue());
      }

      return new LinkJaxb(p1.getUri(), params);
    }
  }

  public static class LinkJaxb {
    private URI uri;
    private Map<QName, Object> params;

    public LinkJaxb() {
      this(null, null);
    }

    public LinkJaxb(URI uri) {
      this(uri, null);
    }

    public LinkJaxb(URI uri, Map<QName, Object> map) {
      this.uri = uri;
      this.params = map != null ? map : new HashMap<QName, Object>();

    }

    @JsonProperty("href")
    public String getUri() {
      return uri.toString().replace("%3F", "?");
    }

    @JsonAnyGetter
    public Map<QName, Object> getParams() {
      return params;
    }

    public void setUri(URI uri) {
      this.uri = uri;
    }

    public void setParams(Map<QName, Object> params) {
      this.params = params;
    }
  }

  @InjectLinks
  @XmlJavaTypeAdapter(LinkAdapter.class)
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
