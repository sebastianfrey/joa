package com.github.sebastianfrey.joa.models.schema.type;

import java.util.Map;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.sebastianfrey.joa.models.schema.JSONSchema;

@JsonDeserialize(as = GenericType.class)
public interface Defs extends JSONSchema {
  public Map<String, JSONSchema> getDefs();
  public void setDefs(Map<String, JSONSchema> defs);
  public Defs def(String name, JSONSchema def);
}
