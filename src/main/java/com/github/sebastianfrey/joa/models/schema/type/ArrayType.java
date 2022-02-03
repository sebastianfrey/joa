package com.github.sebastianfrey.joa.models.schema.type;

import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;

@JsonDeserialize(as = ArrayType.class)
public class ArrayType extends GenericType<ArrayType> {
  private JSONSchema items;
  private List<JSONSchema> prefixItems;
  private Boolean uniqueItems;
  private JSONSchema contains;
  private Integer minItems;
  private Integer maxItems;

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
    setItems(items);
    return this;
  }

  public List<JSONSchema> getPrefixItems() {
    return prefixItems;
  }

  public void setPrefixItems(List<JSONSchema> prefixItems) {
    this.prefixItems = prefixItems;
  }

  public ArrayType prefixItems(List<JSONSchema> prefixItems) {
    setPrefixItems(prefixItems);
    return this;
  }

  public Boolean getUniqueItems() {
    return uniqueItems;
  }

  public void setUniqueItems(Boolean uniqueItems) {
    this.uniqueItems = uniqueItems;
  }

  public ArrayType uniqueItems() {
    setUniqueItems(Boolean.TRUE);
    return this;
  }

  public ArrayType noUniqueItems() {
    setUniqueItems(Boolean.FALSE);
    return this;
  }


  public JSONSchema getContains() {
    return contains;
  }

  public void setContains(JSONSchema contains) {
    this.contains = contains;
  }

  public ArrayType contains(JSONSchema contains) {
    setContains(contains);
    return this;
  }

  public Integer getMinItems() {
    return minItems;
  }

  public void setMinItems(Integer minItems) {
    this.minItems = minItems;
  }

  public ArrayType minItems(Integer minItems) {
    setMinItems(minItems);
    return this;
  }

  public Integer getMaxItems() {
    return maxItems;
  }

  public void setMaxItems(Integer maxItems) {
    this.maxItems = maxItems;
  }

  public ArrayType maxItems(Integer maxItems) {
    setMaxItems(maxItems);
    return this;
  }

}
