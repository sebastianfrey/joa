package com.github.joa.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.joa.api.Capabilities;
import com.github.joa.api.Collection;
import com.github.joa.api.Collections;
import com.github.joa.api.Conformance;
import com.github.joa.api.FeatureCollection;
import com.github.joa.resources.beans.FeatureQueryBean;
import com.github.joa.services.CollectionService;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import mil.nga.sf.geojson.Feature;

@Path("/")
@Produces({ MediaType.APPLICATION_JSON, "application/geo+json" })
public class CollectionResource {

  private CollectionService collectionService;

  public CollectionResource(CollectionService collectionService) {
    this.collectionService = collectionService;
  }

  @GET
  public List<String> getServices() throws IOException {
    return collectionService.getServices();
  }

  @GET
  @Path("/{serviceId}/")
  public Capabilities getCapabilities(@PathParam("serviceId") String serviceId) {
    return collectionService.getCapabilities(serviceId);
  }

  @POST
  @Path("/{serviceId}/collections")
  public Response getCollections(
      @FormDataParam("file") FormDataBodyPart body) {
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
  @Path("/{serviceId}/conformance")
  public Conformance getConformance(@PathParam("serviceId") String serviceId) {
    return collectionService.getConformance(serviceId);
  }

  @GET
  @Path("/{serviceId}/collections")
  public Collections getCollections(@PathParam("serviceId") String serviceId) {
    return collectionService.getCollections(serviceId);
  }

  @GET
  @Path("/{serviceId}/collections/{collectionId}")
  public Collection getCollection(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    return collectionService.getCollection(serviceId, collectionId);
  }

  @GET
  @Path("/{serviceId}/collections/{collectionId}/items")
  public FeatureCollection getItems(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId,
      @BeanParam @Valid FeatureQueryBean featureQuery) {
    return collectionService.getItems(serviceId, collectionId, featureQuery);
  }

  @GET
  @Path("/{serviceId}/collections/{collectionId}/items/{featureId}")
  public Feature getItem(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId, @PathParam("featureId") Long featureId) {
    return collectionService.getItem(serviceId, collectionId, featureId);
  }
}
