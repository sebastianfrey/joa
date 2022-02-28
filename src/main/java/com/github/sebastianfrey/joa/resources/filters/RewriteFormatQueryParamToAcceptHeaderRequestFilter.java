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

  private static final Map<String, String> acceptedHeaders =
      Collections.unmodifiableMap(Map.of(MediaType.TEXT_HTML, "html", MediaType.APPLICATION_JSON,
          "json", MediaType.APPLICATION_GEO_JSON, "json", MediaType.APPLICATION_OPENAPI_JSON,
          "json", MediaType.APPLICATION_OPENAPI_YAML, "yaml"));

  private static final Map<String, String> mappings =
      Collections.unmodifiableMap(Map.of("html", MediaType.TEXT_HTML, "json",
          MediaType.APPLICATION_JSON, "yaml", MediaType.APPLICATION_OPENAPI_YAML));

  @Override
  public void filter(ContainerRequestContext request) throws IOException {
    // analyze URI path whether it ends with .json | .yaml
    String uri = request.getUriInfo().getPath();
    String[] parts = uri.split("\\.");
    String last = parts[parts.length - 1];

    // and if so return
    if (mappings.containsKey(last)) {
      return;
    }

    String format = request.getUriInfo().getQueryParameters().getFirst("f");

    if (format == null) {
      String acceptHeader = request.getHeaderString(HttpHeaders.ACCEPT);
      if (acceptHeader != null) {
        for (String acceptHeaderPart : acceptHeader.split(",")) {
          String accept = acceptHeaderPart.trim();
          if (acceptedHeaders.containsKey(accept)) {
            format = acceptedHeaders.get(accept);
            break;
          }
        }
      }
    }

    if (format == null) {
      format = "html";
    }

    final String mediaType = mappings.get(format);
    if (mediaType != null) {
      request.getHeaders().putSingle(HttpHeaders.ACCEPT, mediaType);
    } else {
      throw new BadRequestException("Invalid parameter: Format '" + format + "' is not supported.");
    }
  }
}
