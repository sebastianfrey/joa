package com.github.sebastianfrey.joa.schemas.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.schemas.JSONSchema;
import com.github.sebastianfrey.joa.schemas.type.GenericType;

@JsonDeserialize(as = GenericType.class)
public interface NotComposition extends JSONSchema {
  public JSONSchema getNot();
  public void setNot(JSONSchema not);
}
