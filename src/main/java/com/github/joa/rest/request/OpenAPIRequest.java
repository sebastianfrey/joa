package com.github.joa.rest.request;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import io.dropwizard.validation.OneOf;

public class OpenAPIRequest {

  @QueryParam("f")
  @DefaultValue("json")
  @OneOf({"json", "yaml"})
  String format;

  @Context
  UriInfo uriInfo;

  public String getFormat() {
    return format;
  }

  public UriInfo getUriInfo() {
    return uriInfo;
  }
}
