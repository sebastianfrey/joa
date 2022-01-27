package com.github.sebastianfrey.joa.services;

import com.github.sebastianfrey.joa.models.FeatureQuery;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Collections;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Item;
import com.github.sebastianfrey.joa.models.Items;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;

/**
 * Defines the base interface for JOA FeatureService implementations.
 *
 * @param <F> The feature type for {@link Items}
 * @param <G> The Geometry type for {@link Item}
 *
 * @author sfrey
 */
public interface FeatureService<F, G>  {
  /**
   * returns a list all available OGC API services.
   * @return List of available services
   */
  public Services getServices();

  /**
   * returns a specific OGC API service by its id.
   *
   * @param serviceId The ID of the service.
   *
   * @return The service instance
   */
  public Service getService(String serviceId);

  /**
   * returns the supported conformance classes for a specific service.
   *
   * @param serviceId The ID of the service.
   *
   * @return The supported conformance classes.
   */
  public Conformance getConformance(String serviceId);

  /**
   * returns the collections for a specific service.
   *
   * @param serviceId The ID of the service.
   *
   * @return The list of all collections.
   */
  public Collections getCollections(String serviceId);

  /**
   * returns a specific OGC API collection from a service by its id.
   *
   * @param serviceId The ID of the service.
   * @param collectionId The of the collection.
   *
   * @return The collection instance
   */
  public Collection getCollection(String serviceId, String collectionId);

  /**
   * returns the items (FeatureCollection) from an OGC API collection from a service using the given query.
   *
   * @param serviceId The ID of the service.
   * @param collectionId The ID of the collection.
   * @param query The query
   *
   * @return The queried items
   */
  public Items<F> getItems(String serviceId, String collectionId, FeatureQuery query);

  /**
   * returns a specific item (Feature) from an OGC API collection from a service by its id.
   *
   * @param serviceId The ID of the service.
   * @param collectionId The ID of the collection.
   * @param featureId The ID of the item.
   *
   * @return The specific item
   */
  public Item<G> getItem(String serviceId, String collectionId, Long featureId);
}
