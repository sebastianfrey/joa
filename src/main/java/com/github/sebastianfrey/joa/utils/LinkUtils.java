package com.github.sebastianfrey.joa.utils;

import java.net.URI;
import java.util.function.Consumer;
import javax.ws.rs.core.Link;
import com.github.sebastianfrey.joa.extensions.ExtendedUriBuilder;

public class LinkUtils {

  public static Link replaceQuery(Link link, Consumer<ExtendedUriBuilder> updateQuery) {
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
