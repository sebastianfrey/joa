package com.github.sebastianfrey.joa.models.schema.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = IntegerType.class)
public class IntegerType extends GenericNumberType<IntegerType, Integer> {
  @Override
  public String getType() {
    return "integer";
  }
}
