package com.github.sebastianfrey.joa.models;

import java.util.List;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.sebastianfrey.joa.utils.LinkUtils;

@JsonPropertyOrder({"title", "description", "links"})
public class Service extends Linkable {
  private String serviceId;
  private String description;

  public Service(String serviceId) {
    this(serviceId, "");
  }

  public Service(String serviceId, String description) {
    this.serviceId = serviceId;
    this.description = description;
  }

  public String getTitle() {
    return serviceId;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String title) {
    this.serviceId = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public List<Link> getLinks() {
    List<Link> links = super.getLinks();

    for (Link link : links) {
      int index = links.indexOf(link);

      Link newLink = LinkUtils.replaceQuery(link, (uriBuilder) -> {
        switch (link.getRel()) {
          case LinkRel.SERVICE_DESC:
            switch (link.getType()) {
              case MediaType.APPLICATION_OPENAPI_JSON:
                uriBuilder.replaceQueryParam("f", "json");
                break;
              case MediaType.APPLICATION_OPENAPI_YAML:
                uriBuilder.replaceQueryParam("f", "yaml");
                break;
            }
            break;
        }
      });

      links.set(index, newLink);
    }

    return links;
  }
}
