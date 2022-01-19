package com.github.joa.api;

import java.util.ArrayList;
import java.util.List;

/**
 * The conformance class.
 *
 * @author sfrey
 */
public class Conformance {

  public final static String FEATURES_CORE = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/core";
  public final static String FEATURES_OAS30 = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/oas30";
  public final static String FEATURES_GEOJSON = "http://www.opengis.net/spec/ogcapi-features-1/1.0/conf/geojson";

  private List<String> conformsTo = new ArrayList<>();

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
