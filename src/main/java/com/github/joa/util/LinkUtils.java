package com.github.joa.util;

import java.util.List;

import javax.ws.rs.core.Link;

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
}
