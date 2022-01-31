package com.github.sebastianfrey.joa.schemas.type;

import com.github.sebastianfrey.joa.schemas.JSONSchema;

public class NullType implements JSONSchema {
  public String getType() {
    return "null";
  }
}
