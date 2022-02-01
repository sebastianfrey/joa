package com.github.sebastianfrey.joa.models.schema.type;

import com.github.sebastianfrey.joa.models.schema.JSONSchema;

public class NullType implements JSONSchema {
  public String getType() {
    return "null";
  }
}
