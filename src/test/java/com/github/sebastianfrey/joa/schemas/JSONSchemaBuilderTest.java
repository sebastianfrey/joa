package com.github.sebastianfrey.joa.schemas;

import static io.dropwizard.testing.FixtureHelpers.*;
import org.junit.jupiter.api.Test;

import static com.github.sebastianfrey.joa.schemas.JSONSchemaBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class JSONSchemaBuilderTest {
  private final static ObjectMapper MAPPER = new ObjectMapper();

  @Test
  public void should_serialize_array_type() throws Exception {
    ArrayType arrayType = arrayType();

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/array.json"), ArrayType.class));

    assertThat(MAPPER.writeValueAsString(arrayType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_boolean_type() throws Exception {
    BooleanType booleanType = booleanType();

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/boolean.json"), BooleanType.class));

    assertThat(MAPPER.writeValueAsString(booleanType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_const_type() throws Exception {
    ConstType constType = constOf("constant");

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/const.json"), ConstType.class));

    assertThat(MAPPER.writeValueAsString(constType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_defs() throws Exception {
    Defs defs = defs().def("name", stringType());

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/defs.json"), Defs.class));

    assertThat(MAPPER.writeValueAsString(defs)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_enum_type() throws Exception {
    EnumType enumType = enumOf("A", "B", "C");

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/enum.json"), EnumType.class));

    assertThat(MAPPER.writeValueAsString(enumType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_generic_type() throws Exception {
    GenericType<?> genericType = genericType().title("title")
        .description("description")
        .defaultValue("default")
        .examples(List.of("test", 3, true))
        .readOnly()
        .writeOnly()
        .deprecated()
        .id("http://localhost/api/examples/point1/queryables?f=json")
        .schema("https://json-schema.org/draft/2019-09/schema");

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/generic.json"), GenericType.class));

    assertThat(MAPPER.writeValueAsString(genericType)).isEqualTo(expected);
  }


  @Test
  public void should_serialize_integer_type() throws Exception {
    IntegerType integerType = integerType();

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/integer.json"), IntegerType.class));

    assertThat(MAPPER.writeValueAsString(integerType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_null_type() throws Exception {
    NullType nullType = nullType();

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/null.json"), NullType.class));

    assertThat(MAPPER.writeValueAsString(nullType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_number_type() throws Exception {
    NumberType numberType = numberType();

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/number.json"), NumberType.class));

    assertThat(MAPPER.writeValueAsString(numberType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_object_type() throws Exception {
    ObjectType objectType = objectType();

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/object.json"), ObjectType.class));

    assertThat(MAPPER.writeValueAsString(objectType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_full_object_type() throws Exception {
    ObjectType objectType = objectType().title("title")
        .property("string", stringType().title("string"))
        .property("number", numberType().title("number"))
        .patternProperty("^_S", stringType())
        .required(List.of("string"))
        .additionalProperties()
        .unevaluatedProperties()
        .minProperties(1)
        .maxProperties(2)
        .propertyNames("^[A-Za-z_][A-Za-z0-9_]*$");

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/objectFull.json"), ObjectType.class));

    assertThat(MAPPER.writeValueAsString(objectType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_ref() throws Exception {
    Ref ref = ref("#/$defs/name");

    final String expected = MAPPER
        .writeValueAsString(MAPPER.readValue(fixture("fixtures/schemas/type/ref.json"), Ref.class));

    assertThat(MAPPER.writeValueAsString(ref)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_string_type() throws Exception {
    StringType stringType = stringType();

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/type/string.json"), StringType.class));

    assertThat(MAPPER.writeValueAsString(stringType)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_oneOf_composition() throws Exception {
    OneOfComposition oneOfComposition = oneOf(stringType(), numberType());

    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/schemas/composition/oneOf.json"), OneOfComposition.class));

    assertThat(MAPPER.writeValueAsString(oneOfComposition)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_anyOf_composition() throws Exception {
    AnyOfComposition anyOfComposition = anyOf(stringType(), numberType());

    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/schemas/composition/anyOf.json"), AnyOfComposition.class));

    assertThat(MAPPER.writeValueAsString(anyOfComposition)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_allOf_composition() throws Exception {
    AllOfComposition allOfComposition = allOf(stringType(), numberType());

    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/schemas/composition/allOf.json"), AllOfComposition.class));

    assertThat(MAPPER.writeValueAsString(allOfComposition)).isEqualTo(expected);
  }

  @Test
  public void should_serialize_not_composition() throws Exception {
    NotComposition notComposition = not(stringType());

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/schemas/composition/not.json"), NotComposition.class));

    assertThat(MAPPER.writeValueAsString(notComposition)).isEqualTo(expected);
  }
}
