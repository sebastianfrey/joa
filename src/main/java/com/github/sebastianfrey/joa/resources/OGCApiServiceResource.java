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
import com.github.sebastianfrey.joa.resources.views.CollectionView;
import com.github.sebastianfrey.joa.resources.views.CollectionsView;
import com.github.sebastianfrey.joa.resources.views.ServiceView;
import com.github.sebastianfrey.joa.resources.views.ServicesView;
import com.github.sebastianfrey.joa.services.OGCApiService;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.model.Resource;

@Path("/")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_GEO_JSON})
public class OGCApiServiceResource {

  @Inject
  private OGCApiService ogcApiService;

  public OGCApiServiceResource() {}

  public OGCApiServiceResource(OGCApiService ogcApiService) {
    this.ogcApiService = ogcApiService;
  }

  @GET
  @ProvideLink(value = Services.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
      style = InjectLink.Style.ABSOLUTE, title = "This document as HTML")
  @ProvideLink(value = Services.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      style = InjectLink.Style.ABSOLUTE, title = "This document as JSON")
  public ServicesView getServices() throws IOException {
    Services services = ogcApiService.getServices();
    return new ServicesView(services);
  }

  @GET
  @Path("{serviceId}")
  @ProvideLink(value = Service.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE, title = "This document as HTML")
  @ProvideLink(value = Service.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE, title = "This document as JSON")
  public ServiceView getCapabilities(@PathParam("serviceId") String serviceId) {
    Service service = ogcApiService.getService(serviceId);
    return new ServiceView(service);
  }

  @POST
  public Response addService(@FormDataParam("file") FormDataBodyPart body) throws Exception {
    ogcApiService.addService(body);
    return Response.ok("Data uploaded successfully !!").build();
  }

  @GET
  @Path("{serviceId}/conformance")
  @ProvideLink(value = Conformance.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
      style = InjectLink.Style.ABSOLUTE, title = "This document as HTML")
  @ProvideLink(value = Conformance.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
      style = InjectLink.Style.ABSOLUTE, title = "This document as JSON")
  @ProvideLink(value = Service.class, rel = Linkable.CONFORMANCE, type = MediaType.TEXT_HTML,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
      style = InjectLink.Style.ABSOLUTE, title = "Conformance classes as HTML")
  @ProvideLink(value = Service.class, rel = Linkable.CONFORMANCE, type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
      style = InjectLink.Style.ABSOLUTE, title = "Conformance classes as JSON")
  public Conformance getConformance(@PathParam("serviceId") String serviceId) {
    return ogcApiService.getConformance(serviceId);
  }

  @GET
  @Path("{serviceId}/collections")
  @ProvideLink(value = Collections.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE, title = "This document as HTML")
  @ProvideLink(value = Collections.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE, title = "This document as JSON")
  @ProvideLink(value = Service.class, rel = Linkable.DATA, type = MediaType.TEXT_HTML,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE, title = "Collections as HTML")
  @ProvideLink(value = Service.class, rel = Linkable.DATA, type = MediaType.APPLICATION_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE, title = "Collections as JSON")
  public CollectionsView getCollections(@PathParam("serviceId") String serviceId) {
    Collections collections = ogcApiService.getCollections(serviceId);
    return new CollectionsView(collections);
  }

  @Path("{serviceId}/api")
  @ProvideLink(value = Service.class, rel = Linkable.SERVICE_DESC,
      type = MediaType.APPLICATION_OPENAPI_JSON,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE, title = "OpenAPI document as JSON")
  @ProvideLink(value = Service.class, rel = Linkable.SERVICE_DESC,
      type = MediaType.APPLICATION_OPENAPI_YAML,
      bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
      style = InjectLink.Style.ABSOLUTE, title = "OpenAPI document as YAML")
  @Produces({MediaType.APPLICATION_OPENAPI_JSON, MediaType.APPLICATION_OPENAPI_YAML})
  public Resource getApi() {
    return Resource.from(OpenAPIResource.class);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}")
  @ProvideLink(value = Collection.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      style = InjectLink.Style.ABSOLUTE, title = "Collection as HTML")
  @ProvideLink(value = Collection.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      style = InjectLink.Style.ABSOLUTE, title = "Collection as JSON")
  @ProvideLink(value = Item.class, rel = Linkable.COLLECTION,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.APPLICATION_JSON, style = InjectLink.Style.ABSOLUTE)
  public CollectionView getCollection(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    Collection collection = ogcApiService.getCollection(serviceId, collectionId);
    return new CollectionView(collection);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/items")
  @ProvideLink(value = Items.class, rel = Linkable.SELF,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.TEXT_HTML, style = InjectLink.Style.ABSOLUTE,
      title = "This document as HTML")
  @ProvideLink(value = Items.class, rel = Linkable.SELF,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE,
      title = "This document as JSON")
  @ProvideLink(value = Collection.class, rel = Linkable.ITEMS,
      type = MediaType.APPLICATION_GEO_JSON,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),},
      style = InjectLink.Style.ABSOLUTE)
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
  public Items<?> getItems(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId,
      @BeanParam @Valid FeatureQueryRequest featureQuery) throws Exception {
    final Set<String> queryables = getQueryables(serviceId, collectionId).getColumns();

    // validate query parameters
    featureQuery.validateQueryParameters(queryables);

    return ogcApiService.getItems(serviceId, collectionId, featureQuery);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/items/{featureId}")
  @ProvideLink(value = Item.class, rel = Linkable.SELF,
      bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
          @Binding(name = "collectionId", value = "${instance.collectionId}"),
          @Binding(name = "featureId", value = "${instance.id}"),},
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE)
  public Item<?> getItem(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId, @PathParam("featureId") Long featureId) {
    return ogcApiService.getItem(serviceId, collectionId, featureId);
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
      type = MediaType.APPLICATION_GEO_JSON, style = InjectLink.Style.ABSOLUTE, title="Queryables in this collection")
  public Queryables getQueryables(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    return ogcApiService.getQueryables(serviceId, collectionId);
  }
}
