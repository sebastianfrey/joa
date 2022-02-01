package com.github.sebastianfrey.joa.models.schema.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;

@JsonDeserialize(as = GenericType.class)
public interface Ref extends JSONSchema {
  public String getRef();
  public void setRef(String ref);
}
