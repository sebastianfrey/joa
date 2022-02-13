package com.github.sebastianfrey.joa.resources.filters;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import com.github.sebastianfrey.joa.models.MediaType;
import com.google.common.net.HttpHeaders;

/**
 * Rewrites the format query parameter as Accept-Header, to use jerseys automatic message bod writer
 * detection.
 *
 * @author sfrey
 */
@Provider
@PreMatching
@Priority(3000)
public class RewriteFormatQueryParamToAcceptHeaderRequestFilter implements ContainerRequestFilter {

  private static final Map<String, String> mappings = Collections
      .unmodifiableMap(Map.of("html", MediaType.TEXT_HTML, "json", MediaType.APPLICATION_JSON));

  @Override
  public void filter(ContainerRequestContext request) throws IOException {
    final String format = request.getUriInfo().getQueryParameters().getFirst("f");
    if (format != null) {
      final String mediaType = mappings.get(format);
      if (mediaType != null) {
        request.getHeaders().putSingle(HttpHeaders.ACCEPT, mediaType);
      } else {
        throw new BadRequestException(
            "Invalid parameter: Format '" + format + "' is not supported.");
      }
    }
  }
}
