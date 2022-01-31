package com.github.sebastianfrey.joa.schemas.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.schemas.JSONSchema;

@JsonDeserialize(as = GenericType.class)
public interface ConstType extends JSONSchema {
  public Object getConstValue();
  public void setConstValue(Object value);
}
