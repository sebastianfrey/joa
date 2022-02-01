package com.github.sebastianfrey.joa.models.schema.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;
import com.github.sebastianfrey.joa.models.schema.type.GenericType;

@JsonDeserialize(as = GenericType.class)
public interface NotComposition extends JSONSchema {
  public JSONSchema getNot();
  public void setNot(JSONSchema not);
}
