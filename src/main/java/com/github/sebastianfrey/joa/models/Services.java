package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Services model is a wrapper to list all available {@link Service} instances.
 */
@JsonPropertyOrder({"services", "links"})
public class Services extends Linkable implements Iterable<Service> {
  List<Service> services = new ArrayList<>();

    /**
   * returns the services property from a Services instance
   *
   * @return List of available services
   */
  public List<Service> getServices() {
    return services;
  }

  public void setServices(List<Service> services) {
    this.services = services;
  }

  public Services services(List<Service> services) {
    setServices(services);
    return this;
  }

  public void addService(Service service) {
    services.add(service);
  }

  public Services service(Service service) {
    addService(service);
    return this;
  }

  @Override
  public Iterator<Service> iterator() {
    return services.iterator();
  }
}
