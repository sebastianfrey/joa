package com.github.joa.rest.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import com.github.joa.core.Service;
import com.github.joa.core.Collection;
import com.github.joa.core.Collections;
import com.github.joa.core.Conformance;
import com.github.joa.core.Item;
import com.github.joa.core.Items;
import com.github.joa.core.MediaType;
import com.github.joa.core.Services;
import com.github.joa.rest.request.ApiRequest;
import com.github.joa.rest.request.FeatureQueryRequest;
import com.github.joa.services.CollectionService;

import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.ProvideLinks;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.integration.api.OpenApiReader;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;

@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_GEO_JSON})
public class CollectionResource {

  private CollectionService collectionService;

  public CollectionResource(CollectionService collectionService) {
    this.collectionService = collectionService;
  }

  @GET
  @ProvideLink(value = Services.class, rel = "self", type = MediaType.APPLICATION_JSON,
      style = InjectLink.Style.ABSOLUTE)
  public Services getServices() throws IOException {
    return collectionService.getServices();
  }

  @GET
  @Path("{serviceId}")
  @ProvideLink(value = Service.class, rel = "self", type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  public Service getCapabilities(@PathParam("serviceId") String serviceId) {
    return collectionService.getService(serviceId);
  }

  @POST
  @Path("{serviceId}/collections")
  public Response getCollections(@FormDataParam("file") FormDataBodyPart body) {
    String UPLOAD_PATH = "/workspaces/joa/data/";

    for (BodyPart part : body.getParent().getBodyParts()) {
      InputStream fileInputStream = part.getEntityAs(InputStream.class);
      ContentDisposition fileMetaData = part.getContentDisposition();

      try {
        int read = 0;
        byte[] bytes = new byte[1024];

        OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileMetaData.getFileName()));
        while ((read = fileInputStream.read(bytes)) != -1) {
          out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
      } catch (IOException e) {
        throw new WebApplicationException("Error while uploading file. Please try again !!");
      }
    }

    return Response.ok("Data uploaded successfully !!").build();
  }

  @GET
  @Path("{serviceId}/conformance")
  @ProvideLinks({
      @ProvideLink(value = Service.class, rel = "conformance", type = MediaType.APPLICATION_JSON,
          bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
          style = InjectLink.Style.ABSOLUTE),
      @ProvideLink(value = Service.class,
          rel = "http://www.opengis.net/def/rel/ogc/1.0/conformance",
          type = MediaType.APPLICATION_JSON,
          bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
          style = InjectLink.Style.ABSOLUTE),})
  @ProvideLink(value = Conformance.class, rel = "self", type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
      style = InjectLink.Style.ABSOLUTE)
  public Conformance getConformance(@PathParam("serviceId") String serviceId) {
    return collectionService.getConformance(serviceId);
  }

  @GET
  @Path("{serviceId}/collections")
  @ProvideLink(value = Service.class, rel = "data", type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Collections.class, rel = "self", type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  public Collections getCollections(@PathParam("serviceId") String serviceId) {
    return collectionService.getCollections(serviceId);
  }

  @GET
  @Path("{serviceId}/api")
  @ProvideLinks({
      @ProvideLink(value = Service.class, rel = "service-desc",
          type = MediaType.APPLICATION_OPENAPI_JSON,
          bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
          style = InjectLink.Style.ABSOLUTE),
      @ProvideLink(value = Service.class, rel = "service-desc",
          type = MediaType.APPLICATION_OPENAPI_YAML,
          bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
          style = InjectLink.Style.ABSOLUTE),})
  @Produces({MediaType.APPLICATION_OPENAPI_JSON, MediaType.APPLICATION_OPENAPI_YAML})
  public Response getApi(@PathParam("serviceId") String serviceId,
      @BeanParam @Valid ApiRequest apiQuery) throws Exception {
    URI uri = apiQuery.getUriInfo().getBaseUriBuilder().host("localhost")
        .path("/openapi." + apiQuery.getFormat()).build();

    String entity = ClientBuilder.newClient().target(uri).request().get(String.class);

    OpenAPI openAPI;

    if (apiQuery.getFormat().equals("json")) {
      openAPI = Json.mapper().readValue(entity, OpenAPI.class);
    } else {
      openAPI = Yaml.mapper().readValue(entity, OpenAPI.class);
    }

    openAPI.getPaths().entrySet().stream().forEach((entry) -> {
      PathItem item = entry.getValue();
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
    });

    String response;

    if (apiQuery.getFormat().equals("json")) {
      response = Json.pretty(openAPI);
    } else {
      response = Yaml.pretty(openAPI);
    }

    response = response.replace("/{serviceId}/", "/");
    response = response.replace("/{serviceId}", "/");

    String type = apiQuery.getFormat().equals("json") ? MediaType.APPLICATION_JSON
        : MediaType.APPLICATION_OPENAPI_YAML;

    return Response.ok(response, type).build();
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}")
  @ProvideLink(value = Collection.class, rel = "self", type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Item.class, rel = "collection",
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.APPLICATION_JSON, style = InjectLink.Style.ABSOLUTE)
  public Collection getCollection(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    return collectionService.getCollection(serviceId, collectionId);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/items")
  @ProvideLink(value = Collection.class, rel = "items", type = MediaType.APPLICATION_GEO_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = "self",
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = "next",
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      condition = "${instance.nextPageAvailable}", type = MediaType.APPLICATION_GEO_JSON,
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = "prev",
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      condition = "${instance.prevPageAvailable}", type = MediaType.APPLICATION_GEO_JSON,
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = "first",
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      condition = "${instance.firstPageAvailable}", type = MediaType.APPLICATION_GEO_JSON,
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = "last",
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      condition = "${instance.lastPageAvailable}", type = MediaType.APPLICATION_GEO_JSON,
      style = InjectLink.Style.ABSOLUTE)
  public Items getItems(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId,
      @BeanParam @Valid FeatureQueryRequest featureQuery) {
    return collectionService.getItems(serviceId, collectionId, featureQuery);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/items/{featureId}")
  @ProvideLink(value = Item.class, rel = "self",
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),
          @Binding(name = "featureId", value = "${instance.id}"),},
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE)
  public Item getItem(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId, @PathParam("featureId") Long featureId) {
    return collectionService.getItem(serviceId, collectionId, featureId);
  }
}
