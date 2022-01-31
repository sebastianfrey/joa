package com.github.sebastianfrey.joa.schemas;

import java.util.Collection;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

public class JSONSchemaResolver extends StdTypeResolverBuilder {

  @Override
  public TypeDeserializer buildTypeDeserializer(final DeserializationConfig config,
      final JavaType baseType, final Collection<NamedType> subtypes) {
    return new JSONSchemaDeserializer(baseType, null, _typeProperty, _typeIdVisible, null);
  }
}
