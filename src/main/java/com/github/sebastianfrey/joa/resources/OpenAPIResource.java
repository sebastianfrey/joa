package com.github.sebastianfrey.joa.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.sebastianfrey.joa.resources.processor.OpenAPIProcessor;
import com.github.sebastianfrey.joa.resources.request.OpenAPIRequest;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;

@Path("/")
public class OpenAPIResource {

  private final static List<String> unusedSchemas =
      List.of("BodyPart", "ContentDisposition", "FormDataBodyPart", "FormDataContentDisposition",
          "MessageBodyWorkers", "MultiPart", "ParameterizedHeader");

  @GET
  public Response getApi(@PathParam("serviceId") String serviceId,
      @BeanParam @Valid OpenAPIRequest query) throws JsonProcessingException {
    // the openapi generate by swagger does include {serviceId} path parameters. In order to be
    // OGC compliant we must get rid of them.
    return new OpenAPIProcessor(query).fetch().process((openAPI) -> {
      String url = query.getUriInfo().getBaseUriBuilder().path(serviceId).toString();

      openAPI.setServers(List.of(new Server().url(url)));

      Paths paths = openAPI.getPaths();
      Paths shortedPaths = new Paths();

      shortedPaths.setExtensions(paths.getExtensions());

      paths.keySet().stream().forEach((path) -> {
        PathItem item = paths.get(path);

        if (item.getGet() != null) {
          if (item.getGet().getParameters() != null) {
            for (Parameter parameter : item.getGet().getParameters()) {
              if (parameter.getName().equals("serviceId")) {
                item.getGet().getParameters().remove(parameter);
                break;
              }
            }
          }
        }

        path = path.replace("/{serviceId}/", "/");
        path = path.replace("/{serviceId}", "/");

        shortedPaths.put(path, item);
      });

      openAPI.setPaths(shortedPaths);

      Map<String, Schema> schemas = openAPI.getComponents().getSchemas();
      Map<String, Schema> filteredSchemas = new HashMap<>();

      schemas.forEach((name, schema) -> {
        if (unusedSchemas.contains(name)) {
          return;
        }

        filteredSchemas.put(name, schema);
      });

      openAPI.getComponents().setSchemas(filteredSchemas);
    }).send();
  }
}
