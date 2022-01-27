package com.github.sebastianfrey.joa.resources.processor;

import java.net.URI;
import java.util.function.Consumer;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.resources.request.OpenAPIRequest;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;

/**
 * OpenAPI Processor to transform the root OpenAPI document to be OGC API compliant.
 *
 * @author sfrey
 */
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

  public OpenAPIProcessor process(Consumer<OpenAPI> processor) throws JsonProcessingException {
    if (openAPI == null) {
      throw new IllegalStateException("OpenAPI document must be fetched before it can be processed.");
    }

    processor.accept(openAPI);

    return this;
  }

  public Response send() {
    String response = getResponse();
    String type = getType();

    return Response.ok(response, type).build();
  }

  private String getResponse() {
    if (openAPI == null) {
      throw new IllegalStateException("OpenAPI document must be fetched before it can be send.");
    }

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
