package com.github.joa.services;

import com.github.joa.core.Service;
import com.github.joa.core.Collection;
import com.github.joa.core.Collections;
import com.github.joa.core.Conformance;
import com.github.joa.core.Items;
import com.github.joa.core.FeatureQuery;
import com.github.joa.core.Item;
import com.github.joa.core.Services;


public interface CollectionService {
  public Services getServices();
  public Service getService(String serviceId);
  public Conformance getConformance(String serviceId);
  public Collections getCollections(String serviceId);
  public Collection getCollection(String serviceId, String collectionId);
  public Items getItems(String serviceId, String collectionId, FeatureQuery query);
  public Item getItem(String serviceId, String collectionId, Long featureId);
}
