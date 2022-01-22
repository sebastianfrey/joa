package com.github.joa.services;

import com.github.joa.core.Capabilities;
import com.github.joa.core.Collection;
import com.github.joa.core.Collections;
import com.github.joa.core.Conformance;
import com.github.joa.core.FeatureCollection;
import com.github.joa.core.FeatureQuery;
import com.github.joa.core.Services;

import mil.nga.sf.geojson.Feature;


public interface CollectionService {
  public Services getServices();
  public Capabilities getCapabilities(String serviceId);
  public Conformance getConformance(String serviceId);
  public Collections getCollections(String serviceId);
  public Collection getCollection(String serviceId, String collectionId);
  public FeatureCollection getItems(String serviceId, String collectionId, FeatureQuery query);
  public Feature getItem(String serviceId, String collectionId, Long featureId);
}
