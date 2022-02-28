package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Collection;

public class CollectionView extends LinkableView {

  private final Collection collection;

  public CollectionView(Collection collection) {
    super("Collection.ftl", collection);

    this.collection = collection;
  }

  @JsonValue
  public Collection getCollection() {
    return collection;
  }
}
