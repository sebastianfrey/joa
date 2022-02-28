package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.linking.InjectLinks;

/**
 * The Linkable model is the base model for all other OGC API model classes.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/link.yaml"
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Linkable {
  private final static ObjectMapper objectMapper = new ObjectMapper();

  /**
   * The conformance relationship.
   */
  public final static String CONFORMANCE = "conformance";

  /**
   * The data relationship.
   */
  public final static String DATA = "data";

  /**
   * The collection relationship.
   */
  public final static String COLLECTION = "collection";

  /**
   * The items relationship.
   */
  public final static String ITEMS = "items";

  /**
   * The pagination next relationship.
   */
  public final static String NEXT = "next";

  /**
   * The pagination previous relationship.
   */
  public final static String PREV = "prev";

  /**
   * The pagination first relationship.
   */
  public final static String FIRST = "first";

  /**
   * The pagination last relationship.
   */
  public final static String LAST = "last";

  /**
   * The self relationship.
   */
  public final static String SELF = "self";

  /**
   * The alternate relationship.
   */
  public final static String ALTERNATE = "alternate";

  /**
   * The OpenAPI service-desc relationship.
   */
  public final static String SERVICE_DESC = "service-desc";

  /**
   * The OpenAPI service-doc relationship.
   */
  public final static String SERVICE_DOC = "service-doc";

  /**
   * The OpenAPI queryables relationship.
   */
  public final static String QUERYABLES = "http://www.opengis.net/def/rel/ogc/1.0/queryables";

  @InjectLinks
  private List<Link> links = new ArrayList<>();

  /**
   * returns the links property from a Linkable instance
   *
   * @return List of navigable links
   */
  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public void addLink(Link link) {
    this.links.add(link);
  }

  /**
   * returns the links of a given rel.
   *
   * @param rel
   * @return
   */
  public List<Link> getLinksByRel(String ...rel) {

    List<String> rels = List.of(rel);

    return getLinks().stream().filter((link) -> {
      return rels.contains(link.getRel());
    }).toList();
  }

  /**
   * returns the first link of a given rel.
   *
   * @param rel
   * @return
   */
  public Link getFirstLinkByRel(String rel) {
    return getLinks().stream().filter((link) -> link.getRel().equalsIgnoreCase(rel)).findFirst().orElse(null);
  }

  /**
   * returns the first link of a given rel.
   *
   * @param rel
   * @return
   */
  public Link getFirstLinkByRelAndType(String rel, String type) {
    return getLinks().stream()
        .filter(
            (link) -> link.getRel().equalsIgnoreCase(rel) && link.getType().equalsIgnoreCase(type))
        .findFirst()
        .orElse(null);
  }

  public String toJSON() throws JsonProcessingException {
    return objectMapper.writeValueAsString(this);
  }
}
