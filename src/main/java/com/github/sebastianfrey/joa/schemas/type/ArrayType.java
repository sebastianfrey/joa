package com.github.sebastianfrey.joa.schemas.type;

import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.schemas.JSONSchema;

@JsonDeserialize(as = ArrayType.class)
public class ArrayType extends GenericType<ArrayType> {
  private JSONSchema items;
  private List<JSONSchema> prefixItems;
  private Boolean uniqueItems;
  private JSONSchema contains;

  @Override
  public String getType() {
    return "array";
  }

  public JSONSchema getItems() {
    return items;
  }

  public void setItems(JSONSchema items) {
    this.items = items;
  }

  public ArrayType items(JSONSchema items) {
    this.items = items;
    return this;
  }

  public List<JSONSchema> getPrefixItems() {
    return prefixItems;
  }

  public void setPrefixItems(List<JSONSchema> prefixItems) {
    this.prefixItems = prefixItems;
  }

  public ArrayType prefixItems(List<JSONSchema> prefixItems) {
    this.prefixItems = prefixItems;
    return this;
  }

  public Boolean getUniqueItems() {
    return uniqueItems;
  }

  public void setUniqueItems(Boolean uniqueItems) {
    this.uniqueItems = uniqueItems;
  }

  public ArrayType uniqueItems() {
    this.uniqueItems = Boolean.TRUE;
    return this;
  }

  public ArrayType noUniqueItems() {
    this.uniqueItems = Boolean.FALSE;
    return this;
  }


  public JSONSchema getContains() {
    return contains;
  }

  public void setContains(JSONSchema contains) {
    this.contains = contains;
  }

  public ArrayType contains(JSONSchema contains) {
    this.contains = contains;
    return this;
  }
}
