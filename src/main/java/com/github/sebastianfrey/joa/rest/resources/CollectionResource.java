package com.github.sebastianfrey.joa.rest.resources;

import java.io.IOException;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.github.sebastianfrey.joa.core.Collection;
import com.github.sebastianfrey.joa.core.Collections;
import com.github.sebastianfrey.joa.core.Conformance;
import com.github.sebastianfrey.joa.core.Item;
import com.github.sebastianfrey.joa.core.Items;
import com.github.sebastianfrey.joa.core.MediaType;
import com.github.sebastianfrey.joa.core.Service;
import com.github.sebastianfrey.joa.core.Services;
import com.github.sebastianfrey.joa.rest.request.FeatureQueryRequest;
import com.github.sebastianfrey.joa.services.CollectionService;
import com.github.sebastianfrey.joa.services.UploadService;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.model.Resource;

@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_GEO_JSON})
public class CollectionResource {

  @Inject
  private CollectionService collectionService;

  @Inject
  private UploadService uploadService;

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
  public Response getCollections(@FormDataParam("file") FormDataBodyPart body) throws Exception {
    uploadService.addService(body);

    return Response.ok("Data uploaded successfully !!").build();
  }

  @GET
  @Path("{serviceId}/conformance")
  @ProvideLink(value = Service.class, rel = "conformance", type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
      style = InjectLink.Style.ABSOLUTE)
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

  @Path("{serviceId}/api")
  @ProvideLink(value = Service.class, rel = "service-desc",
      type = MediaType.APPLICATION_OPENAPI_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Service.class, rel = "service-desc",
      type = MediaType.APPLICATION_OPENAPI_YAML,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  @Produces({MediaType.APPLICATION_OPENAPI_JSON, MediaType.APPLICATION_OPENAPI_YAML})
  public Resource getApi() {
    return Resource.from(OpenAPIResource.class);
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
