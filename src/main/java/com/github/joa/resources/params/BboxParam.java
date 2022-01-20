package com.github.joa.resources.params;

import java.util.List;
import java.util.stream.Stream;

import javax.ws.rs.WebApplicationException;

import mil.nga.sf.GeometryEnvelope;

public class BboxParam {

  public static final String SEPARATOR = ",";

  private List<Double> values;

  public BboxParam(String value) throws Exception {
    values = parse(value);
  }

  public List<Double> parse(String input) throws Exception {
    if (input == null) {
      return null;
    }

    try {
      return Stream.of(input.trim().split(SEPARATOR))
          .map((part) -> Double.valueOf(part))
          .toList();
    } catch (NumberFormatException e) {
      throw new WebApplicationException("query param contains invalid number.", 400);
    }
  }

  public Boolean validate() {
    if (values == null || values.isEmpty()) {
      return true;
    }

    if (values.size() == 4) {
      return true;
    }

    if (values.size() == 6) {
      return true;
    }

    return false;
  }

  public GeometryEnvelope get() {
    Boolean hasZ = values.size() > 4;

    int i = 0;

    Double minX = values.get(i++);
    Double minY = values.get(i++);
    Double minZ = hasZ ? values.get(i++) : null;
    Double maxX = values.get(i++);
    Double maxY = values.get(i++);
    Double maxZ = hasZ ? values.get(i++) : null;

    return new GeometryEnvelope(minX, minY, minZ, maxX, maxY, maxZ);
  }
}
