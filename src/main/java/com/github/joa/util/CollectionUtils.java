package com.github.joa.util;

import java.util.List;

import com.github.joa.core.Collection;

import mil.nga.geopackage.contents.Contents;
import mil.nga.geopackage.features.user.FeatureDao;

public class CollectionUtils {
  public static Collection createCollection(String serviceId, FeatureDao featureDao) {
    Contents contents = featureDao.getContents();

    Collection collection = new Collection();

    String id = contents.getTableName();
    String identifier = contents.getIdentifier();
    String description = contents.getDescription();
    String crs = "http://www.opengis.net/def/crs/EPSG/0/" + contents.getSrsId();

    collection.setServiceId(serviceId);
    collection.setCollectionId(id);
    collection.setDescription(description);
    collection.setTitle(identifier);
    collection.setCrs(List.of(crs));
    collection.setItemType("feature");
    collection.getExtent().getSpatial().addBbox(contents.getBoundingBox());
/*     collection.addLink(Link.builder()
      .withRel("items")
      .withHref("http://localhost:8080/api/pot_WEG_Luebz_linie/collections/" + id + "/items").build()); */

    return collection;
  }
}
