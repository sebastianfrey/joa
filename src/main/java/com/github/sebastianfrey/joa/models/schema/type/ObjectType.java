package com.github.sebastianfrey.joa.models.schema.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;

@JsonDeserialize(as = ObjectType.class)
public class ObjectType extends GenericType<ObjectType> {
  private Map<String, JSONSchema> properties = new HashMap<>();
  private Map<String, JSONSchema> patternProperties = new HashMap<>();
  private List<String> required;
  private Boolean additionalProperties;
  private Boolean unevaluatedProperties;
  private PatternType propertyNames;
  private Integer minProperties;
  private Integer maxProperties;

  public String getType() {
    return "object";
  }

  public Map<String, JSONSchema> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, JSONSchema> properties) {
    this.properties = properties;
  }

  public ObjectType properties(Map<String, JSONSchema> properties) {
    this.properties.putAll(properties);
    return this;
  }

  public ObjectType property(String key, JSONSchema type) {
    this.properties.put(key, type);
    return this;
  }

  public Map<String, JSONSchema> getPatternProperties() {
    return patternProperties;
  }

  public void setPatternProperties(Map<String, JSONSchema> patternProperties) {
    this.patternProperties = patternProperties;
  }

  public ObjectType patternProperties(Map<String, JSONSchema> properties) {
    this.patternProperties.putAll(properties);
    return this;
  }

  public ObjectType patternProperty(String key, JSONSchema type) {
    this.patternProperties.put(key, type);
    return this;
  }

  public List<String> getRequired() {
    return required;
  }

  public void setRequired(List<String> required) {
    this.required = required;
  }

  public ObjectType required(List<String> required) {
    this.required = required;
    return this;
  }

  public Boolean getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(Boolean additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  public ObjectType additionalProperties() {
    this.additionalProperties = Boolean.TRUE;
    return this;
  }

  public ObjectType noAdditionalProperties() {
    this.additionalProperties = Boolean.FALSE;
    return this;
  }

  public Boolean getUnevaluatedProperties() {
    return unevaluatedProperties;
  }

  public void setUnevaluatedProperties(Boolean unevaluatedProperties) {
    this.unevaluatedProperties = unevaluatedProperties;
  }

  public ObjectType unevaluatedProperties() {
    this.unevaluatedProperties = Boolean.TRUE;
    return this;
  }

  public ObjectType noUnevaluatedProperties() {
    this.unevaluatedProperties = Boolean.FALSE;
    return this;
  }

  @JsonProperty("propertyNames")
  public PatternType getPropertyNames() {
    return propertyNames;
  }

  @JsonProperty("propertyNames")
  public void setPropertyNames(PatternType propertyNames) {
    this.propertyNames = propertyNames;
  }

  public void setPropertyNames(String pattern) {
    this.propertyNames = new PatternType(pattern);
  }

  public ObjectType propertyNames(String pattern) {
    this.propertyNames = new PatternType(pattern);
    return this;
  }

  public Integer getMinProperties() {
    return minProperties;
  }

  public void setMinProperties(Integer minProperties) {
    this.minProperties = minProperties;
  }

  public ObjectType minProperties(Integer minProperties) {
    this.minProperties = minProperties;
    return this;
  }

  public Integer getMaxProperties() {
    return maxProperties;
  }

  public void setMaxProperties(Integer maxProperties) {
    this.maxProperties = maxProperties;
  }

  public ObjectType maxProperties(Integer maxProperties) {
    this.maxProperties = maxProperties;
    return this;
  }


}
