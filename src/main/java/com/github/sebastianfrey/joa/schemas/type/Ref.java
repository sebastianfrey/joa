package com.github.sebastianfrey.joa.schemas.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.schemas.JSONSchema;

@JsonDeserialize(as = GenericType.class)
public interface Ref extends JSONSchema {
  public String getRef();
  public void setRef(String ref);
}
