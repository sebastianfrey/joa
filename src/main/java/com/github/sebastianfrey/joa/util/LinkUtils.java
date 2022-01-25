package com.github.sebastianfrey.joa.util;

import java.net.URI;
import java.util.function.Consumer;
import javax.ws.rs.core.Link;
import com.github.sebastianfrey.joa.util.ext.EnhancedUriBuilder;

public class LinkUtils {

  public static Link replaceQuery(Link link, Consumer<EnhancedUriBuilder> updateQuery) {
    Link.Builder linkBuidler = Link.fromLink(link);
    EnhancedUriBuilder uriBuilder = new EnhancedUriBuilder(link.getUriBuilder());

    if (updateQuery != null) {
      updateQuery.accept(uriBuilder);
    }

    URI newUri = uriBuilder.build();
    linkBuidler.uri(newUri);

    Link newLink = linkBuidler.build();
    return newLink;
  }
}
