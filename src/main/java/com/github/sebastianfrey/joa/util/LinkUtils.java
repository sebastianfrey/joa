package com.github.sebastianfrey.joa.util;

import java.net.URI;
import java.util.List;
import java.util.function.Consumer;
import javax.ws.rs.core.Link;
import com.github.sebastianfrey.joa.util.ext.EnhancedUriBuilder;

public class LinkUtils {
  public static String makeAbsolute(String path, List<Link> links) {
    for (Link link : links) {
      if (link.getUri().isAbsolute()) {
        if (!path.startsWith("/")) {
          path = "/" + path;
        }

        return link.getUri().getScheme() + "://" + link.getUri().getAuthority() + path;
      }
    }

    return path;
  }

  public static Link replaceQuery(Link link, String query) {
    return LinkUtils.replaceQuery(link, query, null);
  }

  public static Link replaceQuery(Link link, Consumer<EnhancedUriBuilder> updateQuery) {
    return LinkUtils.replaceQuery(link, null, updateQuery);
  }

  public static Link replaceQuery(Link link, String query, Consumer<EnhancedUriBuilder> updateQuery) {
    Link.Builder linkBuidler = Link.fromLink(link);
    EnhancedUriBuilder uriBuilder = new EnhancedUriBuilder(link.getUriBuilder());

    if (query != null) {
      uriBuilder.replaceQuery(query);
    }

    if (updateQuery != null) {
      updateQuery.accept(uriBuilder);
    }

    URI newUri = uriBuilder.build();
    linkBuidler.uri(newUri);

    Link newLink = linkBuidler.build();
    return newLink;
  }
}
