package com.github.sebastianfrey.joa.resources.views;

public class OpenAPIView extends ContextAwareView {
  private final String serviceId;

  public OpenAPIView(String serviceId) {
    super("OpenAPI.ftl");
    this.serviceId = serviceId;
  }

  public String getServiceId() {
    return serviceId;
  }
}
