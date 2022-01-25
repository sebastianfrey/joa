package com.github.sebastianfrey.joa.services;

import com.github.sebastianfrey.joa.core.Collection;
import com.github.sebastianfrey.joa.core.Collections;
import com.github.sebastianfrey.joa.core.Conformance;
import com.github.sebastianfrey.joa.core.FeatureQuery;
import com.github.sebastianfrey.joa.core.Item;
import com.github.sebastianfrey.joa.core.Items;
import com.github.sebastianfrey.joa.core.Service;
import com.github.sebastianfrey.joa.core.Services;


public interface CollectionService {
  public Services getServices();
  public Service getService(String serviceId);
  public Conformance getConformance(String serviceId);
  public Collections getCollections(String serviceId);
  public Collection getCollection(String serviceId, String collectionId);
  public Items getItems(String serviceId, String collectionId, FeatureQuery query);
  public Item getItem(String serviceId, String collectionId, Long featureId);
}
