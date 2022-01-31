package com.github.sebastianfrey.joa.schemas.composition;

import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.schemas.JSONSchema;
import com.github.sebastianfrey.joa.schemas.type.GenericType;

@JsonDeserialize(as = GenericType.class)
public interface OneOfComposition extends JSONSchema {
  public List<JSONSchema> getOneOf();
  public void setOneOf(List<JSONSchema> oneOf);
}
