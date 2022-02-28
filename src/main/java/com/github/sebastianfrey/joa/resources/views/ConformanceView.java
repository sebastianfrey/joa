package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Conformance;

public class ConformanceView extends LinkableView {
  private final Conformance conformance;

  public ConformanceView(Conformance conformance) {
    super("Conformance.ftl", conformance);
    this.conformance = conformance;
  }

  @JsonValue
  public Conformance getConformance() {
    return conformance;
  }
}
