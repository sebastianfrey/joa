package com.github.sebastianfrey.joa.schemas;

import java.util.List;
import com.github.sebastianfrey.joa.schemas.composition.AllOfComposition;
import com.github.sebastianfrey.joa.schemas.composition.AnyOfComposition;
import com.github.sebastianfrey.joa.schemas.composition.NotComposition;
import com.github.sebastianfrey.joa.schemas.composition.OneOfComposition;
import com.github.sebastianfrey.joa.schemas.type.ArrayType;
import com.github.sebastianfrey.joa.schemas.type.BooleanType;
import com.github.sebastianfrey.joa.schemas.type.ConstType;
import com.github.sebastianfrey.joa.schemas.type.Defs;
import com.github.sebastianfrey.joa.schemas.type.EnumType;
import com.github.sebastianfrey.joa.schemas.type.GenericType;
import com.github.sebastianfrey.joa.schemas.type.IntegerType;
import com.github.sebastianfrey.joa.schemas.type.NullType;
import com.github.sebastianfrey.joa.schemas.type.NumberType;
import com.github.sebastianfrey.joa.schemas.type.ObjectType;
import com.github.sebastianfrey.joa.schemas.type.Ref;
import com.github.sebastianfrey.joa.schemas.type.StringType;

public class JSONSchemaBuilder {

  private static <T extends GenericType<T>> GenericType<T> genericType() {
    return new GenericType<>();
  }

  public static ObjectType objectType() {
    return new ObjectType();
  }

  public static StringType stringType() {
    return new StringType();
  }

  public static BooleanType booleanType() {
    return new BooleanType();
  }

  public static NumberType numberType() {
    return new NumberType();
  }

  public static IntegerType integerType() {
    return new IntegerType();
  }

  public static NullType nullType() {
    return new NullType();
  }

  public static ArrayType arrayType() {
    return new ArrayType();
  }

  public static EnumType enumOf(Object ...enumValues) {
    return enumOf(List.of(enumValues));
  }

  public static EnumType enumOf(List<Object> enumValues) {
    return genericType().enumValues(enumValues);
  }

  public static ConstType constOf(Object constValue) {
    return genericType().constValue(constValue);
  }

  public static AllOfComposition allOf(JSONSchema ...allOf) {
    return genericType().allOf(List.of(allOf));
  }

  public static AllOfComposition allOf(List<JSONSchema> allOf) {
    return genericType().allOf(allOf);
  }

  public static OneOfComposition oneOf(JSONSchema ...oneOf) {
    return genericType().oneOf(List.of(oneOf));
  }

  public static OneOfComposition oneOf(List<JSONSchema> oneOf) {
    return genericType().oneOf(oneOf);
  }

  public static AnyOfComposition anyOf(JSONSchema ...anyOf) {
    return genericType().anyOf(List.of(anyOf));
  }

  public static AnyOfComposition anyOf(List<JSONSchema> anyOf) {
    return genericType().anyOf(anyOf);
  }

  public static NotComposition not(JSONSchema not) {
    return genericType().not(not);
  }

  public static Ref ref(String ref) {
    return genericType().ref(ref);
  }

  public static Defs defs() {
    return genericType();
  }
}
