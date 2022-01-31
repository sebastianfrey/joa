package com.github.sebastianfrey.joa.schemas.type;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.schemas.JSONSchema;

@JsonDeserialize(as = GenericType.class)
public interface EnumType extends JSONSchema {
  @JsonProperty("enum")
  public List<Object> getEnumValues();
  @JsonProperty("enum")
  public void setEnumValues(List<Object> values);
}
