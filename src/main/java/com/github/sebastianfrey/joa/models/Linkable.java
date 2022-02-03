package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

/**
 * The Linkable model is the base model for all other OGC API model classes.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/link.yaml"
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Linkable {

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
   * The OpenAPI service-desc relationship.
   */
  public final static String SERVICE_DESC = "service-desc";

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
}
