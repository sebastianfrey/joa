package com.github.sebastianfrey.joa.models.schema.composition;

import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;
import com.github.sebastianfrey.joa.models.schema.type.GenericType;

@JsonDeserialize(as = GenericType.class)
public interface AllOfComposition extends JSONSchema {
  public List<JSONSchema> getAllOf();
  public void setAllOf(List<JSONSchema> allOf);
}