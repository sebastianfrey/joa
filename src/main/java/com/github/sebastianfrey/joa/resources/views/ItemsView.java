package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Items;

public class ItemsView extends LinkableView {
  private final Items<?> items;

  public ItemsView(Items<?> items) {
    super("Items.ftl", items);

    this.items = items;
  }

  @JsonValue
  public Items<?> getItems() {
    return items;
  }
}
