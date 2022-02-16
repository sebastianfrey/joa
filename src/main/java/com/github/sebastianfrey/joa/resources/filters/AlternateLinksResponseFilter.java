package com.github.sebastianfrey.joa.resources.filters;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
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
  private static final Map<String, String> mappings =
      Collections.unmodifiableMap(Map.of(MediaType.TEXT_HTML, "html", MediaType.APPLICATION_JSON,
          "json", MediaType.APPLICATION_GEO_JSON, "json"));

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    Object entity = responseContext.getEntity();

    try {
      processLinks(entity, requestContext);
    } catch (IllegalAccessException ex) {
      throw new IOException(ex);
    }
  }

  public void processLinks(Object entity, ContainerRequestContext requestContext)
      throws IllegalAccessException {
    if (entity instanceof LinkableView) {
      LinkableView linkableView = (LinkableView) entity;
      entity = linkableView.getLinkable();
    }

    if (entity instanceof Linkable) {
      Linkable linkable = (Linkable) entity;
      List<Link> links = linkable.getLinks();
      String acceptHeader = requestContext.getHeaderString(HttpHeaders.ACCEPT);
      String targetFormat = mappings.get(acceptHeader);

      for (Link link : links) {
        String rel = link.getRel();
        String linkFormat = mappings.get(link.getType());

        if (linkFormat != null) {
          int index = links.indexOf(link);

          Link alternateLink = LinkUtils.transformUri(link, (uriBuilder) -> {
            uriBuilder.replaceQueryParam("f", linkFormat);
          });

          // set rel='alternate' for links with rel='self', when they are not the requested
          // output format
          if (Linkable.SELF.equals(rel) && linkFormat != null && targetFormat != null
              && !linkFormat.equals(targetFormat)) {
            // set rel to ALTERNATE
            alternateLink = Link.fromUri(alternateLink.getUri())
                .rel(Linkable.ALTERNATE)
                .title(alternateLink.getTitle())
                .type(alternateLink.getType())
                .build();
          }

          links.set(index, alternateLink);
        }
      }

      processLinkable(entity, requestContext);
    }
  }

  @SuppressWarnings("unchecked")
  public void processLinkable(Object entity, ContainerRequestContext requestContex)
      throws IllegalAccessException {
    for (Field field : entity.getClass().getDeclaredFields()) {
      field.setAccessible(true);

      Object entityField = field.get(entity);

      if (entityField instanceof Collection) {
        Collection<Object> collection = (Collection<Object>) entityField;

        for (Object child : collection) {
          processLinks(child, requestContex);
        }
      }
    }
  }
}
