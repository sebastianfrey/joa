package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Collections;

public class CollectionsView extends LinkableView {

  private final Collections collections;

  public CollectionsView(Collections collections) {
    super("Collections.ftl", collections);

    this.collections = collections;
  }

  @JsonValue
  public Collections getCollections() {
    return collections;
  }
}
