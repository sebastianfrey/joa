package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Services;

public class ServicesView extends LinkableView {
  private final Services services;

  public ServicesView(Services services) {
    super("Services.ftl", services);
    this.services = services;
  }

  @JsonValue
  public Services getServices() {
    return services;
  }
}
