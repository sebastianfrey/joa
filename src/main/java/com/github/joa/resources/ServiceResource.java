package com.github.joa.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.joa.db.GeoPackageService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceResource {
  GeoPackageService geopackageService;
  CollectionResource collectionResource;

  public ServiceResource(GeoPackageService geopackageService, CollectionResource collectionResource) {
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
