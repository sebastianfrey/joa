package com.github.sebastianfrey.joa.models.schema.type;

import com.fasterxml.jackson.annotation.JsonSetter;

public abstract class GenericNumberType<T extends GenericNumberType<T, K>, K>
    extends GenericType<GenericNumberType<T, K>> {
  private Integer multipleOf;
  private K minimum;
  private K exclusiveMinimum;
  private K maximum;
  private K exclusiveMaximum;

  public Integer getMultipleOf() {
    return multipleOf;
  }

  public void setMultipleOf(Integer multipleOf) {
    this.multipleOf = multipleOf;
  }

  @SuppressWarnings("unchecked")
  public T multipleOf(Integer multipleOf) {
    setMultipleOf(multipleOf);;
    return (T) this;
  }

  public K getMinimum() {
    return minimum;
  }

  @SuppressWarnings("unchecked")
  public T minimum(K minimum) {
    setMinimum(minimum);;
    return (T) this;
  }

  @JsonSetter
  public void setMinimum(K minimum) {
    this.minimum = minimum;
  }

  public K getExclusiveMinimum() {
    return exclusiveMinimum;
  }

  @JsonSetter
  public void setExclusiveMinimum(K exclusiveMinimum) {
    this.exclusiveMinimum = exclusiveMinimum;
  }

  @SuppressWarnings("unchecked")
  public T exclusiveMinimum(K exclusiveMinimum) {
    setExclusiveMaximum(exclusiveMaximum);
    return (T) this;
  }


  public K getMaximum() {
    return maximum;
  }

  @JsonSetter
  public void setMaximum(K maximum) {
    this.maximum = maximum;
  }

  @SuppressWarnings("unchecked")
  public T maximum(K maximum) {
    this.maximum = maximum;
    return (T) this;
  }

  public K getExclusiveMaximum() {
    return exclusiveMaximum;
  }

  @JsonSetter
  public void setExclusiveMaximum(K exclusiveMaximum) {
    this.exclusiveMaximum = exclusiveMaximum;
  }

  @SuppressWarnings("unchecked")
  public T exclusiveMaximum(K exclusiveMaximum) {
    this.exclusiveMaximum = exclusiveMaximum;
    return (T) this;
  }
}
