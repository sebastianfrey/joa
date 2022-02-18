package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Item;

public class ItemView extends LinkableView {
  private final Item<?> item;

  public ItemView(Item<?> item) {
    super("Item.ftl", item);

    this.item = item;
  }

  @JsonValue
  public Item<?> getItem() {
    return item;
  }
}
