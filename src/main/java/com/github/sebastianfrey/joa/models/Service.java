package com.github.sebastianfrey.joa.models;

import java.util.List;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.sebastianfrey.joa.utils.LinkUtils;

/**
 * The Service model represents an OGC API landing page type.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/landingPage.yaml"
 */
@JsonPropertyOrder({"title", "description", "links"})
public class Service extends Linkable {
  private String serviceId;
  private String title;
  private String description;

  /**
   * returns the serviceId property from a Service instance
   *
   * @return String serviceId
   */
  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Service serviceId(String serviceId) {
    setServiceId(serviceId);
    return this;
  }

  /**
   * returns the title property from a Service instance
   *
   * @return String title
   */
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Service title(String title) {
    setTitle(title);
    return this;
  }

  /**
   * returns the description property from a Service instance
   *
   * @return String description
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Service description(String description) {
    setDescription(description);
    return this;
  }

  @Override
  public List<Link> getLinks() {
    List<Link> links = super.getLinks();

    for (Link link : links) {
      int index = links.indexOf(link);

      Link newLink = LinkUtils.transformUri(link, (uriBuilder) -> {
        switch (link.getRel()) {
          case Linkable.SERVICE_DESC:
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
