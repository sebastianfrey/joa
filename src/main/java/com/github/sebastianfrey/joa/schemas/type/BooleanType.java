package com.github.sebastianfrey.joa.schemas.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = BooleanType.class)
public class BooleanType extends GenericType<BooleanType>{
  @Override
  public String getType() {
    return "boolean";
  }
}
