package com.github.sebastianfrey.joa.models.schema.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = IntegerType.class)
public class IntegerType extends GenericNumberType<IntegerType, Long> {
  @Override
  public String getType() {
    return "integer";
  }

  public IntegerType exclusiveMaximum(Integer exclusiveMaximum) {
    return exclusiveMaximum(Long.valueOf(exclusiveMaximum));
  }

  public IntegerType exclusiveMinimum(Integer exclusiveMinimum) {
    return exclusiveMinimum(Long.valueOf(exclusiveMinimum));
  }

  public IntegerType maximum(Integer maximum) {
    return maximum(Long.valueOf(maximum));
  }

  public IntegerType minimum(Integer minimum) {
    return minimum(Long.valueOf(minimum));
  }

  public void setExclusiveMaximum(Integer exclusiveMaximum) {
    setExclusiveMaximum(Long.valueOf(exclusiveMaximum));
  }

  public void setExclusiveMinimum(Integer exclusiveMinimum) {
    setExclusiveMinimum(Long.valueOf(exclusiveMinimum));
  }

  public void setMaximum(Integer maximum) {
    setMaximum(Long.valueOf(maximum));
  }

  public void setMinimum(Integer minimum) {
    setMinimum(Long.valueOf(minimum));
  }


}
