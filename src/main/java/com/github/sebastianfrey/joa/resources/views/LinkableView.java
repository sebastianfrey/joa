package com.github.sebastianfrey.joa.resources.views;

import com.github.sebastianfrey.joa.models.Linkable;

public class LinkableView extends LocalizedView {
  private final Linkable linkable;

  public LinkableView(String view, Linkable linkable) {
    super(view);

    this.linkable = linkable;
  }

  public Linkable getLinkable() {
    return linkable;
  }
}
