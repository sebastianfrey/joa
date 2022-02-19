package com.github.sebastianfrey.joa.resources.processor;

import java.net.URI;
import java.util.function.Consumer;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.resources.request.OpenAPIRequest;
import com.github.sebastianfrey.joa.resources.views.OpenAPIView;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;

/**
 * OpenAPI Processor to transform the root OpenAPI document to be OGC API compliant.
 *
 * @author sfrey
 */
public class OpenAPIProcessor {
  private final String serviceId;
  private final String format;
  private final UriInfo uriInfo;

  private OpenAPI openAPI;

  public OpenAPIProcessor(OpenAPIRequest options) {
    this.serviceId = options.getServiceId();
    this.format = options.getFormat();
    this.uriInfo = options.getUriInfo();
  }

  public OpenAPIProcessor fetch() throws JsonProcessingException {
    if (!format.equals("html")) {
      URI uri = uriInfo.getBaseUriBuilder().host("localhost").path("/openapi." + format).build();
      String entity = ClientBuilder.newClient().target(uri).request().get(String.class);

      if (format.equals("yaml")) {
        openAPI = Yaml.mapper().readValue(entity, OpenAPI.class);
      } else {
        openAPI = Json.mapper().readValue(entity, OpenAPI.class);
      }
    }

    return this;
  }

  public OpenAPIProcessor process(Consumer<OpenAPI> processor) throws JsonProcessingException {
    if (openAPI == null) {
      return this;
    }

    processor.accept(openAPI);

    return this;
  }

  public Response send() {
    Object response = getResponse();
    String type = getType();

    return Response.ok(response, type).build();
  }

  private Object getResponse() {
    if (format.equals("yaml")) {
      return Yaml.pretty(openAPI);
    } else if (format.equals("json")) {
      return Json.pretty(openAPI);
    } else {
      return new OpenAPIView(serviceId);
    }
  }

  private String getType() {
    if (format.equals("yaml")) {
      return MediaType.APPLICATION_OPENAPI_YAML;
    } else if (format.equals("json")) {
      return MediaType.APPLICATION_JSON;
    } else {
      return MediaType.TEXT_HTML;
    }
  }
}
