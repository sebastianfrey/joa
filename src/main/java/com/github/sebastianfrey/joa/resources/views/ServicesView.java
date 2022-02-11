package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Services;
import io.dropwizard.views.View;

public class ServicesView extends View {
  private final Services services;

  public ServicesView(Services services) {
    super("services.ftl");
    this.services = services;
  }

  @JsonValue
  public Services getServices() {
    return services;
  }
}
