package com.github.sebastianfrey.joa.models;

import com.github.sebastianfrey.joa.models.schema.JSONSchema;
import static com.github.sebastianfrey.joa.models.schema.JSONSchemaBuilder.*;
import java.util.List;

public class Schemas {
  public static class GeoJSON {
    public static JSONSchema BoundingBox = arrayType().items(numberType()).minItems(4);

    public static List<String> RequiredProps = List.of("type", "coordinates");

    public static JSONSchema PointCoordinates = arrayType().items(numberType()).minItems(2);

    public static JSONSchema LineStringCoordinates =
        arrayType().items(PointCoordinates).minItems(2);

    public static JSONSchema PolygonCoordinates =
        arrayType().items(arrayType().items(PointCoordinates).minItems(4));

    public static JSONSchema MultiPointCoordinates = arrayType().items(PointCoordinates);

    public static JSONSchema MultiLineStringCoordinates = arrayType().items(LineStringCoordinates);

    public static JSONSchema MultiPolygonCoordinates = arrayType().items(PolygonCoordinates);

    public static JSONSchema Point = objectType().title("GeoJSON Point")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("Point")))
        .property("coordiantes", PointCoordinates)
        .property("bbox", BoundingBox);

    public static JSONSchema LineString = objectType().title("GeoJSON LineString")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("LineString")))
        .property("coordinates", LineStringCoordinates);

    public static JSONSchema Polygon = objectType().title("GeoJSON Polygon")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("Polygon")))
        .property("coordinates", PolygonCoordinates)
        .property("bbox", BoundingBox);

    public static JSONSchema MultiPoint = objectType().title("GeoJSON MultiPoint")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("MultiPoint")))
        .property("coordinates", MultiPointCoordinates)
        .property("bbox", BoundingBox);

    public static JSONSchema MultiLineString = objectType().title("GeoJSON MultiLineString")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("MultiLineString")))
        .property("coordinates", MultiLineStringCoordinates)
        .property("bbox", BoundingBox);

    public static JSONSchema MultiPolygon = objectType().title("GeoJSON MultiPolygon")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("MultiPolygon")))
        .property("coordinates", MultiPolygonCoordinates)
        .property("bbox", BoundingBox);
  }

}
