package com.github.sebastianfrey.joa.resources.views;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.Queryables;

public class QueryablesView extends LinkableView {
  private final Queryables queryables;

  public QueryablesView(Queryables queryables) {
    super("Queryables.ftl", queryables);
    this.queryables = queryables;
  }

  @JsonValue
  public Queryables getQueryables() {
    return queryables;
  }
}
