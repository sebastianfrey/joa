package com.github.sebastianfrey.joa.models.schema.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;

@JsonDeserialize(as = GenericType.class)
public interface ConstType extends JSONSchema {
  public Object getConstValue();
  public void setConstValue(Object value);
}
