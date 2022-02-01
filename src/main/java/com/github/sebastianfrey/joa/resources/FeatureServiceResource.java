package com.github.sebastianfrey.joa.resources;

import java.io.IOException;
import java.util.Set;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Item;
import com.github.sebastianfrey.joa.models.Items;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.models.Queryables;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.resources.request.FeatureQueryRequest;
import com.github.sebastianfrey.joa.services.FeatureService;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.model.Resource;

@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_GEO_JSON})
public class FeatureServiceResource {

  @Inject
  private FeatureService<Object, Object> featureService;

  @GET
  @ProvideLink(value = Services.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      style = InjectLink.Style.ABSOLUTE)
  public Services getServices() throws IOException {
    return featureService.getServices();
  }

  @GET
  @Path("{serviceId}")
  @ProvideLink(value = Service.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  public Service getCapabilities(@PathParam("serviceId") String serviceId) {
    return featureService.getService(serviceId);
  }

  @POST
  public Response getCollections(@FormDataParam("file") FormDataBodyPart body) throws Exception {
    featureService.addService(body);

    return Response.ok("Data uploaded successfully !!").build();
  }

  @GET
  @Path("{serviceId}/conformance")
  @ProvideLink(value = Service.class, rel = Linkable.CONFORMANCE, type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Conformance.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
      style = InjectLink.Style.ABSOLUTE)
  public Conformance getConformance(@PathParam("serviceId") String serviceId) {
    return featureService.getConformance(serviceId);
  }

  @GET
  @Path("{serviceId}/collections")
  @ProvideLink(value = Service.class, rel = Linkable.DATA, type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Collections.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  public Collections getCollections(@PathParam("serviceId") String serviceId) {
    return featureService.getCollections(serviceId);
  }

  @Path("{serviceId}/api")
  @ProvideLink(value = Service.class, rel = Linkable.SERVICE_DESC,
      type = MediaType.APPLICATION_OPENAPI_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Service.class, rel = Linkable.SERVICE_DESC,
      type = MediaType.APPLICATION_OPENAPI_YAML,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE)
  @Produces({MediaType.APPLICATION_OPENAPI_JSON, MediaType.APPLICATION_OPENAPI_YAML})
  public Resource getApi() {
    return Resource.from(OpenAPIResource.class);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}")
  @ProvideLink(value = Collection.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Item.class, rel = Linkable.COLLECTION,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.APPLICATION_JSON, style = InjectLink.Style.ABSOLUTE)
  public Collection getCollection(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    return featureService.getCollection(serviceId, collectionId);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/items")
  @ProvideLink(value = Collection.class, rel = Linkable.ITEMS,
      type = MediaType.APPLICATION_GEO_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = Linkable.SELF,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = Linkable.NEXT,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      condition = "${instance.nextPageAvailable}", type = MediaType.APPLICATION_GEO_JSON,
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = Linkable.PREV,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      condition = "${instance.prevPageAvailable}", type = MediaType.APPLICATION_GEO_JSON,
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = Linkable.FIRST,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      condition = "${instance.firstPageAvailable}", type = MediaType.APPLICATION_GEO_JSON,
      style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Items.class, rel = Linkable.LAST,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      condition = "${instance.lastPageAvailable}", type = MediaType.APPLICATION_GEO_JSON,
      style = InjectLink.Style.ABSOLUTE)
  public Items<Object> getItems(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId,
      @BeanParam @Valid FeatureQueryRequest featureQuery) throws Exception {
    final Set<String> queryables =
        getQueryables(serviceId, collectionId).getSchema().getProperties().keySet();

    // validate query parameters
    featureQuery.validateQueryParameters(queryables);

    return featureService.getItems(serviceId, collectionId, featureQuery);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/items/{featureId}")
  @ProvideLink(value = Item.class, rel = Linkable.SELF,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),
          @Binding(name = "featureId", value = "${instance.id}"),},
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE)
  public Item<Object> getItem(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId, @PathParam("featureId") Long featureId) {
    return featureService.getItem(serviceId, collectionId, featureId);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/queryables")
  @ProvideLink(value = Queryables.class, rel = Linkable.SELF,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE)
  @ProvideLink(value = Collection.class, rel = Linkable.QUERYABLES,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE)
  public Queryables getQueryables(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    return featureService.getQueryables(serviceId, collectionId);
  }
}
