package com.github.sebastianfrey.joa.schemas.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = NumberType.class)
public class NumberType extends GenericNumberType<NumberType, Double> {
  @Override
  public String getType() {
    return "number";
  }

}
