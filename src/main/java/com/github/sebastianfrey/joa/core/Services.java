package com.github.sebastianfrey.joa.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"services", "links"})
public class Services extends Linkable {
  List<Service> services;

  public Services() {
  }

  public Services(Set<Service> services) {
    this(new ArrayList<>(services));
  }

  public Services(List<Service> services) {
    this.services = services;
  }

  public List<Service> getServices() {
    return services;
  }

  public void setServices(List<Service> services) {
    this.services = services;
  }
}
