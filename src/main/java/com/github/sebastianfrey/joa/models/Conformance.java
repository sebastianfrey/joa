package com.github.sebastianfrey.joa.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Conformance model represents the OGC API Conformance type.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/schemas/confClasses.yaml"
 */
@JsonPropertyOrder({"conformsTo", "links"})
@JsonIgnoreProperties({"serviceId"})
public class Conformance extends Linkable {

  public final static String FEATURES_CORE = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/core";
  public final static String FEATURES_OAS30 = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/oas30";
  public final static String FEATURES_GEOJSON = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/geojson";
  public final static String FEATURES_HTML = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/html";

  @JsonIgnore
  private String serviceId;

  private List<String> conformsTo = new ArrayList<>();

  public Conformance() {}

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Conformance serviceId(String serviceId) {
    setServiceId(serviceId);
    return this;
  }

  public List<String> getConformsTo() {
    return conformsTo;
  }

  public void setConformsTo(List<String> conformsTo) {
    this.conformsTo = conformsTo;
  }

  public Conformance conformsTo(List<String> conformsTo) {
    this.conformsTo.addAll(conformsTo);
    return this;
  }

  public void addConformsTo(String link) {
    conformsTo.add(link);
  }

  public Conformance conformsTo(String link) {
    this.conformsTo.add(link);
    return this;
  }
}
