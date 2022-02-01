package com.github.sebastianfrey.joa.models.schema.composition;

import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;
import com.github.sebastianfrey.joa.models.schema.type.GenericType;

@JsonDeserialize(as = GenericType.class)
public interface AnyOfComposition extends JSONSchema {
  public List<JSONSchema> getAnyOf();
  public void setAnyOf(List<JSONSchema> anyOf);
}
