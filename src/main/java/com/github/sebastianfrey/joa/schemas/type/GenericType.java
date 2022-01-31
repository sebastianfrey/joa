package com.github.sebastianfrey.joa.schemas.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.sebastianfrey.joa.schemas.composition.AllOfComposition;
import com.github.sebastianfrey.joa.schemas.composition.AnyOfComposition;
import com.github.sebastianfrey.joa.schemas.composition.NotComposition;
import com.github.sebastianfrey.joa.schemas.composition.OneOfComposition;
import com.github.sebastianfrey.joa.schemas.JSONSchema;

@JsonPropertyOrder({"type", "title", "description"})
public class GenericType<T extends GenericType<T>> implements EnumType, ConstType,
    OneOfComposition, AnyOfComposition, AllOfComposition, NotComposition, Ref, Defs {
  private String title;
  private String type;
  private String description;
  @JsonProperty("default")
  private String defaultValue;
  private List<Object> examples;
  @JsonProperty("enum")
  private List<Object> enumValues;
  private Boolean deprecated;
  private Boolean readOnly;
  private Boolean writeOnly;
  @JsonProperty("$schema")
  private String schema;
  @JsonProperty("$id")
  private String id;
  @JsonProperty("$ref")
  private String ref;
  @JsonProperty("$defs")
  private Map<String, JSONSchema> defs;
  @JsonProperty("const")
  private Object constValue;
  private JSONSchema not;
  public List<JSONSchema> allOf;
  public List<JSONSchema> anyOf;
  public List<JSONSchema> oneOf;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @SuppressWarnings("unchecked")
  public T title(String title) {
    setTitle(title);
    return (T) this;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @SuppressWarnings("unchecked")
  public T description(String description) {
    setDescription(description);
    return (T) this;
  }

  @JsonProperty("default")
  public String getDefaultValue() {
    return defaultValue;
  }

  @JsonProperty("default")
  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  @SuppressWarnings("unchecked")
  public T defaultValue(String defaultValue) {
    setDefaultValue(defaultValue);
    return (T) this;
  }

  public List<Object> getExamples() {
    return examples;
  }

  public void setExamples(List<Object> examples) {
    this.examples = examples;
  }

  @SuppressWarnings("unchecked")
  public T examples(List<Object> examples) {
    setExamples(examples);
    return (T) this;
  }

  @Override
  @JsonProperty("enum")
  public List<Object> getEnumValues() {
    return enumValues;
  }

  @Override
  @JsonProperty("enum")
  public void setEnumValues(List<Object> enumValues) {
    this.enumValues = enumValues;
  }

  @SuppressWarnings("unchecked")
  public T enumValues(List<Object> enumValues) {
    this.enumValues = enumValues;
    return (T) this;
  }

  @Override
  @JsonProperty("const")
  public Object getConstValue() {
    return constValue;
  }

  @Override
  @JsonProperty("const")
  public void setConstValue(Object constValue) {
    this.constValue = constValue;
  }

  @SuppressWarnings("unchecked")
  public T constValue(Object constValue) {
    this.constValue = constValue;
    return (T) this;
  }

  public Boolean getDeprecated() {
    return deprecated;
  }

  public void setDeprecated(Boolean deprecated) {
    this.deprecated = deprecated;
  }

  @SuppressWarnings("unchecked")
  public T deprecated() {
    this.deprecated = Boolean.TRUE;
    return (T) this;
  }

  public Boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }

  @SuppressWarnings("unchecked")
  public T readOnly() {
    this.readOnly = Boolean.TRUE;
    return (T) this;
  }

  public Boolean getWriteOnly() {
    return writeOnly;
  }

  public void setWriteOnly(Boolean writeOnly) {
    this.writeOnly = writeOnly;
  }

  @SuppressWarnings("unchecked")
  public T writeOnly() {
    this.writeOnly = Boolean.TRUE;
    return (T) this;
  }

  @JsonProperty("$schema")
  public String getSchema() {
    return schema;
  }

  @JsonProperty("$schema")
  public void setSchema(String schema) {
    this.schema = schema;
  }

  @SuppressWarnings("unchecked")
  public T schema(String schema) {
    this.schema = schema;
    return (T) this;
  }

  @JsonProperty("$id")
  public String getId() {
    return id;
  }

  @JsonProperty("$id")
  public void setId(String id) {
    this.id = id;
  }

  @SuppressWarnings("unchecked")
  public T id(String id) {
    this.id = id;
    return (T) this;
  }

  @Override
  public JSONSchema getNot() {
    return not;
  }

  @Override
  public void setNot(JSONSchema not) {
    this.not = not;
  }

  @SuppressWarnings("unchecked")
  public T not(JSONSchema not) {
    this.not = not;
    return (T) this;
  }

  @Override
  public List<JSONSchema> getAllOf() {
    return allOf;
  }

  @Override
  public void setAllOf(List<JSONSchema> allOf) {
    this.allOf = allOf;
  }

  @SuppressWarnings("unchecked")
  public T allOf(List<JSONSchema> allOf) {
    this.allOf = allOf;
    return (T) this;
  }

  @Override
  public List<JSONSchema> getAnyOf() {
    return anyOf;
  }

  @Override
  public void setAnyOf(List<JSONSchema> anyOf) {
    this.anyOf = anyOf;
  }

  @SuppressWarnings("unchecked")
  public T anyOf(List<JSONSchema> anyOf) {
    this.anyOf = anyOf;
    return (T) this;
  }

  @Override
  public List<JSONSchema> getOneOf() {
    return oneOf;
  }

  @Override
  public void setOneOf(List<JSONSchema> oneOf) {
    this.oneOf = oneOf;
  }

  @SuppressWarnings("unchecked")
  public T oneOf(List<JSONSchema> oneOf) {
    this.oneOf = oneOf;
    return (T) this;
  }

  @Override
  @JsonProperty("$ref")
  public String getRef() {
    return this.ref;
  }

  @Override
  @JsonProperty("$ref")
  public void setRef(String ref) {
    this.ref = ref;
  }

  @SuppressWarnings("unchecked")
  public T ref(String ref) {
    this.ref = ref;
    return (T) this;
  }

  @Override
  @JsonProperty("$defs")
  public Map<String, JSONSchema> getDefs() {
    return defs;
  }

  @Override
  @JsonProperty("$defs")
  public void setDefs(Map<String, JSONSchema> defs) {
    this.defs = defs;
  }

  @SuppressWarnings("unchecked")
  public T defs(Map<String, JSONSchema> defs) {
    this.defs = defs;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public T def(String key, JSONSchema def) {
    if (this.defs == null) {
      this.defs = new HashMap<>();
    }
    this.defs.put(key, def);
    return (T) this;
  }
}
