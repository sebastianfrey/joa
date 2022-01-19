package com.github.joa.resources.params;

import java.util.List;
import java.util.stream.Stream;

import javax.ws.rs.WebApplicationException;

public class DoubleListParam {

  private List<Double> values;

  public DoubleListParam(String value) throws Exception {
    values = parse(value);
  }

  protected List<Double> parse(String input) throws Exception {
    if (input == null) {
      return null;
    }

    try {
      return Stream.of(input.trim().split(","))
          .map((part) -> Double.valueOf(part))
          .toList();
    } catch (NumberFormatException e) {
      throw new WebApplicationException("query param contains invalid number.", 400);
    }
  }

  public List<Double> get() {
    return values;
  }

}
