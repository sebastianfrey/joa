package com.github.sebastianfrey.joa.models;

import com.github.sebastianfrey.joa.models.schema.JSONSchema;
import static com.github.sebastianfrey.joa.models.schema.JSONSchemaBuilder.*;
import java.util.List;

public class Schemas {

  public static String DRAFT_2019_09 = "https://json-schema.org/draft/2019-09/schema";

  public static class GeoJSON {
    static JSONSchema BoundingBox = arrayType().items(numberType()).minItems(4);

    static List<String> RequiredProps = List.of("type", "coordinates");

    static JSONSchema PointCoordinates = arrayType().items(numberType()).minItems(2);

    static JSONSchema LineStringCoordinates = arrayType().items(PointCoordinates).minItems(2);

    static JSONSchema PolygonCoordinates =
        arrayType().items(arrayType().items(PointCoordinates).minItems(4));

    static JSONSchema MultiPointCoordinates = arrayType().items(PointCoordinates);

    static JSONSchema MultiLineStringCoordinates = arrayType().items(LineStringCoordinates);

    static JSONSchema MultiPolygonCoordinates = arrayType().items(PolygonCoordinates);

    static JSONSchema Point = objectType().title("GeoJSON Point")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("Point")))
        .property("coordiantes", PointCoordinates)
        .property("bbox", BoundingBox);

    static JSONSchema LineString = objectType().title("GeoJSON LineString")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("LineString")))
        .property("coordinates", LineStringCoordinates);

    static JSONSchema Polygon = objectType().title("GeoJSON Polygon")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("Polygon")))
        .property("coordinates", PolygonCoordinates)
        .property("bbox", BoundingBox);

    static JSONSchema MultiPoint = objectType().title("GeoJSON MultiPoint")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("MultiPoint")))
        .property("coordinates", MultiPointCoordinates)
        .property("bbox", BoundingBox);

    static JSONSchema MultiLineString = objectType().title("GeoJSON MultiLineString")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("MultiLineString")))
        .property("coordinates", MultiLineStringCoordinates)
        .property("bbox", BoundingBox);

    static JSONSchema MultiPolygon = objectType().title("GeoJSON MultiPolygon")
        .required(RequiredProps)
        .property("type", stringType().enumValues(List.of("MultiPolygon")))
        .property("coordinates", MultiPolygonCoordinates)
        .property("bbox", BoundingBox);

    static JSONSchema Geometry =
        oneOf(Point, LineString, Polygon, MultiPoint, MultiLineString, MultiPolygon);

    public static JSONSchema geometry() {
      return Geometry;
    }

    /**
     * returns the JSON schema for a GeoJSON Point.
     *
     * @return the GeoJSON Point schema.
     */
    public static JSONSchema point() {
      return Point;
    }

    /**
     * returns the JSON schema for a GeoJSON LineString.
     *
     * @return the GeoJSON LineString schema.
     */
    public static JSONSchema lineString() {
      return LineString;
    }

    /**
     * returns the JSON schema for a GeoJSON Polygon.
     *
     * @return the GeoJSON Polygon schema.
     */
    public static JSONSchema polygon() {
      return Polygon;
    }

    /**
     * returns the JSON schema for a GeoJSON MultiPoint.
     *
     * @return the GeoJSON MultiPoint schema.
     */
    public static JSONSchema multiPoint() {
      return MultiPoint;
    }

    /**
     * returns the JSON schema for a GeoJSON MultiLineString.
     *
     * @return the GeoJSON MultiLineString schema.
     */
    public static JSONSchema multiLineString() {
      return MultiLineString;
    }

    /**
     * returns the JSON schema for a GeoJSON MultiPolygon.
     *
     * @return the GeoJSON MultiPolygon schema.
     */
    public static JSONSchema multiPolygon() {
      return MultiPolygon;
    }
  }
}
