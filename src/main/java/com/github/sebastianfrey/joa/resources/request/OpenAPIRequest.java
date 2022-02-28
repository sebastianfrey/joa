package com.github.sebastianfrey.joa.resources.request;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import com.github.sebastianfrey.joa.models.MediaType;
import io.dropwizard.validation.OneOf;

/**
 * Request bean used for openapi queries.
 *
 * @author sfrey
 */
public class OpenAPIRequest {

  @PathParam("serviceId")
  String serviceId;

  @QueryParam("f")
  @OneOf({"json", "yaml", "html"})
  String format;

  @HeaderParam(HttpHeaders.ACCEPT)
  @OneOf({MediaType.TEXT_HTML, MediaType.APPLICATION_OPENAPI_YAML, MediaType.APPLICATION_JSON,
      MediaType.APPLICATION_OPENAPI_JSON})
  String acceptHeader;

  @Context
  UriInfo uriInfo;

  public String getServiceId() {
    return serviceId;
  }

  public String getFormat() {
    if (acceptHeader != null && format == null) {
      if (acceptHeader.equals(MediaType.TEXT_HTML)) {
        format = "html";
      } else if (acceptHeader.equals(MediaType.APPLICATION_OPENAPI_YAML)) {
        format = "yaml";
      } else if (acceptHeader.equals(MediaType.APPLICATION_OPENAPI_JSON) || acceptHeader.equals(MediaType.APPLICATION_JSON)) {
        format = "json";
      }
    }

    return format;
  }

  public UriInfo getUriInfo() {
    return uriInfo;
  }
}
