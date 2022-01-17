package com.github.geoio.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.geoio.db.GeopackageService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceResource {
  GeopackageService geopackageService;
  CollectionResource collectionResource;

  public ServiceResource(GeopackageService geopackageService, CollectionResource collectionResource) {
    this.geopackageService = geopackageService;
    this.collectionResource = collectionResource;
  }


  @GET
  public List<String> getServices() throws IOException {
    return geopackageService.list();
  }

  @Path("/{serviceId}/")
  public CollectionResource getCollectionResource() {
    return collectionResource;
  }
}
