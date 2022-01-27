package com.github.sebastianfrey.joa.utils;

import java.net.URI;
import java.util.function.Consumer;
import javax.ws.rs.core.Link;
import com.github.sebastianfrey.joa.extensions.jaxrs.ExtendedUriBuilder;

/**
 * JAX-RS Link utilities.
 *
 * @author sfrey
 */
public class LinkUtils {

  /**
   * Transform the URI of a given Link using.
   *
   * @param link The link to transform.
   * @param updateQuery The transform function.
   *
   * @return A new Link instance with the transformed URI.
   */
  public static Link transformUri(Link link, Consumer<ExtendedUriBuilder> updateQuery) {
    Link.Builder linkBuidler = Link.fromLink(link);
    ExtendedUriBuilder uriBuilder = new ExtendedUriBuilder(link.getUriBuilder());

    if (updateQuery != null) {
      updateQuery.accept(uriBuilder);
    }

    URI newUri = uriBuilder.build();
    linkBuidler.uri(newUri);

    Link newLink = linkBuidler.build();
    return newLink;
  }
}
