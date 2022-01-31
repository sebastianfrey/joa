package com.github.sebastianfrey.joa.schemas;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.sebastianfrey.joa.schemas.type.ArrayType;
import com.github.sebastianfrey.joa.schemas.type.BooleanType;
import com.github.sebastianfrey.joa.schemas.type.GenericType;
import com.github.sebastianfrey.joa.schemas.type.IntegerType;
import com.github.sebastianfrey.joa.schemas.type.NullType;
import com.github.sebastianfrey.joa.schemas.type.NumberType;
import com.github.sebastianfrey.joa.schemas.type.ObjectType;
import com.github.sebastianfrey.joa.schemas.type.StringType;

public class JSONSchemaDeserializer extends AsPropertyTypeDeserializer {

  public JSONSchemaDeserializer(final JavaType bt, final TypeIdResolver idRes,
      final String typePropertyName, final boolean typeIdVisible, final JavaType defaultImpl) {
    super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
  }

  public JSONSchemaDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
    super(src, property);
  }

  @Override
  public TypeDeserializer forProperty(final BeanProperty prop) {
    return (prop == _property) ? this : new JSONSchemaDeserializer(this, prop);
  }

  @Override
  public Object deserializeTypedFromObject(final JsonParser jp, final DeserializationContext ctxt)
      throws IOException {
    JsonNode node = jp.readValueAsTree();
    Class<?> subType = findSubType(node);
    JavaType type = TypeFactory.defaultInstance().constructType(subType);

    JsonParser jsonParser = new TreeTraversingParser(node, jp.getCodec());
    if (jsonParser.getCurrentToken() == null) {
      jsonParser.nextToken();
    }

    JsonDeserializer<Object> deser = ctxt.findContextualValueDeserializer(type, _property);
    return deser.deserialize(jsonParser, ctxt);
  }

  protected Class<?> findSubType(JsonNode node) {
    Class<? extends JSONSchema> subType = GenericType.class;

    if (node.has("type") && node.get("type").isTextual()) {
      switch (node.get("type").asText()) {
        case "array":
          subType = ArrayType.class;
          break;
        case "object":
          subType = ObjectType.class;
          break;
        case "string":
          subType = StringType.class;
          break;
        case "integer":
          subType = IntegerType.class;
          break;
        case "boolean":
          subType = BooleanType.class;
          break;
        case "null":
          subType = NullType.class;
          break;
        case "number":
          subType = NumberType.class;
          break;
      }
    }

    return subType;
  }
}
