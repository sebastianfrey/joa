package com.github.joa.services;

import java.util.List;

import com.github.joa.api.Capabilities;
import com.github.joa.api.Collection;
import com.github.joa.api.Collections;
import com.github.joa.api.Conformance;
import com.github.joa.api.FeatureCollection;
import com.github.joa.api.FeatureQuery;

import mil.nga.sf.geojson.Feature;


public interface CollectionService {
  public List<String> getServices();
  public Capabilities getCapabilities(String serviceId);
  public Conformance getConformance(String serviceId);
  public Collections getCollections(String serviceId);
  public Collection getCollection(String serviceId, String collectionId);
  public FeatureCollection getItems(String serviceId, String collectionId, FeatureQuery query);
  public Feature getItem(String serviceId, String collectionId, Long featureId);
}
