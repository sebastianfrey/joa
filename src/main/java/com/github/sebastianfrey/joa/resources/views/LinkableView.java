package com.github.sebastianfrey.joa.resources.views;

import com.github.sebastianfrey.joa.models.Linkable;
import io.dropwizard.views.View;

public class LinkableView extends View {
  Linkable linkable;

  public LinkableView(String view, Linkable linkable) {
    super(view);

    this.linkable = linkable;
  }

  public Linkable getLinkable() {
    return linkable;
  }
}
