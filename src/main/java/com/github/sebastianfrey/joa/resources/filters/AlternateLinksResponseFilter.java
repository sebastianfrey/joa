package com.github.sebastianfrey.joa.resources.filters;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Link;
import javax.ws.rs.ext.Provider;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.resources.views.LinkableView;
import com.github.sebastianfrey.joa.utils.LinkUtils;

/**
 * Post processes the links of {@Link Linkable} instances.
 *
 * @author sfrey
 */
@Provider
@Priority(0)
public class AlternateLinksResponseFilter implements ContainerResponseFilter {
  private static final Map<String, String> mappings = Collections.unmodifiableMap(
      Map.of(MediaType.APPLICATION_JSON, "json", MediaType.APPLICATION_GEO_JSON, "json"));

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    Object entity = responseContext.getEntity();
    if (entity instanceof LinkableView) {
      LinkableView linkableView = (LinkableView) entity;
      entity = linkableView.getLinkable();
    }
    if (entity instanceof Linkable) {
      Linkable linkable = (Linkable) entity;
      List<Link> links = linkable.getLinks();
      for (Link link : links) {
        String extension = mappings.get(link.getType());
        if (extension != null && link.getRel().equals(Linkable.ALTERNATE)) {
          int index = links.indexOf(link);
          Link newLink = LinkUtils.transformUri(link, (uriBuilder) -> {
            uriBuilder.replaceQueryParam("f", extension);
          });
          links.set(index, newLink);
        }
      }
    }
  }
}
