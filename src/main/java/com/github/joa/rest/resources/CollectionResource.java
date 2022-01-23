package com.github.joa.rest.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.github.joa.core.Service;
import com.github.joa.core.Collection;
import com.github.joa.core.Collections;
import com.github.joa.core.Conformance;
import com.github.joa.core.Item;
import com.github.joa.core.Items;
import com.github.joa.core.MediaType;
import com.github.joa.core.Services;
import com.github.joa.rest.request.FeatureQueryRequest;
import com.github.joa.services.CollectionService;

import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
  @Path("{serviceId}/")
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
  @ProvideLink(value = Service.class, rel = "conformance", type = MediaType.APPLICATION_JSON,
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
