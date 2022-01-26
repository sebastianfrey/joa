package com.github.sebastianfrey.joa.services;

import com.github.sebastianfrey.joa.models.FeatureQuery;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Item;
import com.github.sebastianfrey.joa.models.Items;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;

public interface FeatureService<Feature, Geometry>  {
  public Services getServices();
  public Service getService(String serviceId);
  public Conformance getConformance(String serviceId);
  public Collections getCollections(String serviceId);
  public Collection getCollection(String serviceId, String collectionId);
  public Items<Feature> getItems(String serviceId, String collectionId, FeatureQuery query);
  public Item<Geometry> getItem(String serviceId, String collectionId, Long featureId);
}
