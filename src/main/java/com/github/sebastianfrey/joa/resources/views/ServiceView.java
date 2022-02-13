package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Service;

public class ServiceView extends LinkableView {
  private final Service service;

  public ServiceView(Service service) {
    super("Service.ftl", service);

    this.service = service;
  }

  @JsonValue
  public Service getService() {
    return service;
  }
}
