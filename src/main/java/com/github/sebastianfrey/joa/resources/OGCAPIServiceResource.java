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
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.models.Queryables;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.resources.annotations.CollectionLinks;
import com.github.sebastianfrey.joa.resources.annotations.CollectionsLinks;
import com.github.sebastianfrey.joa.resources.annotations.ConformanceLinks;
import com.github.sebastianfrey.joa.resources.annotations.ItemLinks;
import com.github.sebastianfrey.joa.resources.annotations.ItemsLinks;
import com.github.sebastianfrey.joa.resources.annotations.OpenAPILinks;
import com.github.sebastianfrey.joa.resources.annotations.QueryableLinks;
import com.github.sebastianfrey.joa.resources.annotations.ServiceLinks;
import com.github.sebastianfrey.joa.resources.annotations.ServicesLinks;
import com.github.sebastianfrey.joa.resources.request.FeatureQueryRequest;
import com.github.sebastianfrey.joa.resources.views.CollectionView;
import com.github.sebastianfrey.joa.resources.views.CollectionsView;
import com.github.sebastianfrey.joa.resources.views.ConformanceView;
import com.github.sebastianfrey.joa.resources.views.ItemView;
import com.github.sebastianfrey.joa.resources.views.ItemsView;
import com.github.sebastianfrey.joa.resources.views.QueryablesView;
import com.github.sebastianfrey.joa.resources.views.ServiceView;
import com.github.sebastianfrey.joa.resources.views.ServicesView;
import com.github.sebastianfrey.joa.services.OGCAPIService;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.model.Resource;

@Path("/")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
public class OGCAPIServiceResource {

  @Inject
  private OGCAPIService ogcApiService;

  public OGCAPIServiceResource() {}

  public OGCAPIServiceResource(OGCAPIService ogcApiService) {
    this.ogcApiService = ogcApiService;
  }

  @GET
  @ServicesLinks
  public ServicesView getServices() throws IOException {
    Services services = ogcApiService.getServices();
    return new ServicesView(services);
  }

  @GET
  @Path("{serviceId}")
  @ServiceLinks
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
  @ConformanceLinks
  public ConformanceView getConformance(@PathParam("serviceId") String serviceId) {
    Conformance conformance = ogcApiService.getConformance(serviceId);
    return new ConformanceView(conformance);
  }

  @Path("{serviceId}/api")
  @OpenAPILinks
  @Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OPENAPI_JSON,
      MediaType.APPLICATION_OPENAPI_YAML})
  public Resource getApi() {
    return Resource.from(OpenAPIResource.class);
  }

  @GET
  @Path("{serviceId}/collections")
  @CollectionsLinks
  public CollectionsView getCollections(@PathParam("serviceId") String serviceId) {
    Collections collections = ogcApiService.getCollections(serviceId);
    return new CollectionsView(collections);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}")
  @CollectionLinks
  public CollectionView getCollection(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    Collection collection = ogcApiService.getCollection(serviceId, collectionId);
    return new CollectionView(collection);
  }


  @GET
  @Path("{serviceId}/collections/{collectionId}/queryables")
  @QueryableLinks
  public QueryablesView getQueryables(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    Queryables queryables = ogcApiService.getQueryables(serviceId, collectionId);
    return new QueryablesView(queryables);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/items")
  @Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_GEO_JSON})
  @ItemsLinks
  public ItemsView getItems(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId,
      @BeanParam @Valid FeatureQueryRequest featureQuery) throws Exception {
    final Set<String> queryables =
        getQueryables(serviceId, collectionId).getQueryables().getColumns();

    // validate query parameters
    featureQuery.validateQueryParameters(queryables);

    Items<?> items = ogcApiService.getItems(serviceId, collectionId, featureQuery);
    return new ItemsView(items);
  }

  @GET
  @Path("{serviceId}/collections/{collectionId}/items/{featureId}")
  @Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_GEO_JSON})
  @ItemLinks
  public ItemView getItem(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId, @PathParam("featureId") Long featureId) {
    Item<?> item = ogcApiService.getItem(serviceId, collectionId, featureId);
    return new ItemView(item);
  }
}
