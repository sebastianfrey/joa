package com.github.geoio.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.geoio.api.Collection;
import com.github.geoio.api.FeatureCollection;
import com.github.geoio.service.CollectionService;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CollectionResource {

  private CollectionService collectionService;

  public CollectionResource(CollectionService collectionService) {
    this.collectionService = collectionService;
  }

  private static final List<Collection> collections = new ArrayList<>();

  static {
    Collection collection = new Collection();

    collection.setId("test");
    collection.setLinks(new ArrayList<>());

    collections.add(collection);
  }

  @POST
  @Path("/collections")
  public Response getCollections(
      @FormDataParam("file") FormDataBodyPart body) {
    String UPLOAD_PATH = "/workspaces/geoio/data/";

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
  @Path("/collections")
  public List<Collection> getCollections(@PathParam("serviceId") String serviceId) {
    return collectionService.getCollections(serviceId);
  }

  @GET
  @Path("/collections/{collectionId}")
  public Collection getCollection(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    for (Collection collection : collectionService.getCollections(serviceId)) {
      if (collection.getId().equals(collectionId)) {
        return collection;
      }
    }

    throw new NotFoundException("No collection with ID equals " + collectionId + " found.");
  }

  @GET
  @Path("/collections/{collectionId}/items")
  public FeatureCollection getItems(@PathParam("serviceId") String serviceId,
      @PathParam("collectionId") String collectionId) {
    return collectionService.getItems(serviceId, collectionId);
  }
}
