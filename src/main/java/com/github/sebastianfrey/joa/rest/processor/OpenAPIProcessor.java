package com.github.sebastianfrey.joa.rest.processor;

import java.net.URI;
import java.util.function.Consumer;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.sebastianfrey.joa.core.MediaType;
import com.github.sebastianfrey.joa.rest.request.OpenAPIRequest;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;

public class OpenAPIProcessor {
  String format;
  UriInfo uriInfo;
  OpenAPI openAPI;

  public OpenAPIProcessor(OpenAPIRequest options) {
    this.format = options.getFormat();
    this.uriInfo = options.getUriInfo();
  }

  public OpenAPIProcessor fetch() throws JsonProcessingException {
    URI uri = uriInfo.getBaseUriBuilder().host("localhost").path("/openapi." + format).build();
    String entity = ClientBuilder.newClient().target(uri).request().get(String.class);

    if (format.equals("yaml")) {
      openAPI = Yaml.mapper().readValue(entity, OpenAPI.class);
    } else {
      openAPI = Json.mapper().readValue(entity, OpenAPI.class);
    }

    return this;
  }

  public OpenAPIProcessor process(Consumer<OpenAPI> processor) {
    processor.accept(openAPI);

    return this;
  }

  public Response send() {
    String response = getResponse();
    String type = getType();

    return Response.ok(response, type).build();
  }

  private String getResponse() {
    if (format.equals("yaml")) {
      return Yaml.pretty(openAPI);
    } else {
      return Json.pretty(openAPI);
    }
  }

  private String getType() {
    return format.equals("json") ? MediaType.APPLICATION_JSON : MediaType.APPLICATION_OPENAPI_YAML;
  }
}
