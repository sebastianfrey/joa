package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The conformance class.
 *
 * @author sfrey
 */
@JsonPropertyOrder({"conformsTo", "links"})
@JsonIgnoreProperties({"serviceId"})
public class Conformance extends Linkable {

  public final static String FEATURES_CORE = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/core";
  public final static String FEATURES_OAS30 = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/oas30";
  public final static String FEATURES_GEOJSON = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/geojson";

  @JsonIgnore
  private String serviceId;

  private List<String> conformsTo = new ArrayList<>();

  public Conformance(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public List<String> getConformsTo() {
    return conformsTo;
  }

  public void setConformsTo(List<String> conformsTo) {
    this.conformsTo = conformsTo;
  }

  public void addConformsTo(String link) {
    conformsTo.add(link);
  }
}
