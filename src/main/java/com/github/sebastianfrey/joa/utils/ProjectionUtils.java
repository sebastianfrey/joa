package com.github.sebastianfrey.joa.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.github.sebastianfrey.joa.models.Crs;
import org.locationtech.proj4j.ProjCoordinate;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.proj.Projection;
import mil.nga.proj.ProjectionConstants;
import mil.nga.proj.ProjectionFactory;
import mil.nga.proj.ProjectionRetriever;
import mil.nga.proj.ProjectionTransform;
import mil.nga.sf.Geometry;
import mil.nga.sf.GeometryCollection;
import mil.nga.sf.GeometryEnvelope;
import mil.nga.sf.LineString;
import mil.nga.sf.MultiLineString;
import mil.nga.sf.MultiPoint;
import mil.nga.sf.MultiPolygon;
import mil.nga.sf.Point;
import mil.nga.sf.Polygon;

public class ProjectionUtils {

  private static Map<String, Projection> PROJECTIONS;
  private static Set<String> CRS;

  private final static Map<String, Projection> loadProjections() {
    Map<String, Projection> projections = new HashMap<>();

    for (String projectionId : getProjectionIdsByAuthority(ProjectionConstants.AUTHORITY_OGC)) {
      Projection projection =
          ProjectionFactory.getProjection(ProjectionConstants.AUTHORITY_OGC + ":" + projectionId);
      String projectionUri = CrsUtils.parse(String.valueOf(projectionId));
      projections.put(projectionUri, projection);
    }

    for (String projectionId : getProjectionIdsByAuthority(ProjectionConstants.AUTHORITY_EPSG)) {
      Projection projection =
          ProjectionFactory.getProjection(ProjectionConstants.AUTHORITY_EPSG + ":" + projectionId);
      String projectionUri = CrsUtils.parse(String.valueOf(projectionId));
      projections.put(projectionUri, projection);
    }


    return projections;
  }

  private static Set<String> getProjectionIdsByAuthority(String authority) {
    return ProjectionRetriever.getOrCreateProjections(authority)
        .keySet()
        .stream()
        .map((key) -> String.valueOf(key))
        .collect(Collectors.toSet());
  }

  private static void verifyProjections() {
    if (PROJECTIONS == null) {
      PROJECTIONS = loadProjections();
    }
  }

  /**
   * Returns all available projections.
   *
   * @return
   */
  public static Set<Projection> getProjections() {
    verifyProjections();

    return new HashSet<>(PROJECTIONS.values());
  }

  /**
   * Returns all avialble projections.
   *
   * @return
   */
  public static Projection getProjection(String projectionId) {
    verifyProjections();

    Projection projection = PROJECTIONS.get(CrsUtils.parse(projectionId));
    return projection;
  }

  public static Projection getProjection(Crs crs) {
    if (crs == null || crs.getUri() == null) {
      return null;
    }

    return getProjection(crs.getUri());
  }

  public static boolean hasProjection(Crs crs) {
    return getProjection(crs) != null;
  }

  /**
   * Adds a new projection.
   *
   * @param projectionId
   * @param projection
   */
  public static void addProjection(String projectionId, Projection projection) {
    verifyProjections();

    String crs = CrsUtils.parse(projectionId);

    PROJECTIONS.put(crs, projection);

    if (CRS != null) {
      CRS.add(crs);
    }
  }


  public static ProjectionTransform getTransformation(Projection from, Projection to) {
    if (from == null || to == null) {
      return null;
    }

    return from.getTransformation(to);
  }

  public static ProjectionTransform getTransformation(FeatureDao from, Crs to) {
    Projection sourceProjection = from.getProjection();
    Projection targetProjection = ProjectionUtils.getProjection(to);
    return getTransformation(sourceProjection, targetProjection);
  }

  public static ProjectionTransform getTransformation(Crs from, FeatureDao to) {
    Projection sourceProjection = ProjectionUtils.getProjection(from);
    Projection targetProjection = to.getProjection();
    return getTransformation(sourceProjection, targetProjection);
  }


  public static Set<String> getCRSList() {
    verifyProjections();

    if (CRS == null) {
      CRS = new LinkedHashSet<>();

      CRS.add(CrsUtils.CRS84);
      CRS.addAll(PROJECTIONS.keySet());
    }

    return CRS;
  }

  @SuppressWarnings("unchecked")
  public static void reproject(Geometry geometry, ProjectionTransform transformation) {
    switch (geometry.getGeometryType()) {
      case POINT:
        reprojectPoint((Point) geometry, transformation);
        break;
      case LINESTRING:
        reprojectLineString((LineString) geometry, transformation);
        break;
      case POLYGON:
        reprojectPolygon((Polygon) geometry, transformation);
        break;
      case MULTIPOINT:
        reprojectMultiPoint((MultiPoint) geometry, transformation);
        break;
      case MULTILINESTRING:
        reprojectMultiLineString((MultiLineString) geometry, transformation);
        break;
      case MULTIPOLYGON:
        reprojectMultiPolygon((MultiPolygon) geometry, transformation);
        break;
      case GEOMETRYCOLLECTION:
        reprojectGeometryCollection((GeometryCollection<Geometry>) geometry, transformation);
        break;
      default:
        break;
    }
  }

  public static void reprojectPoint(Point point, ProjectionTransform transformation) {
    ProjCoordinate coord = reprojectCoordinate(point.getX(), point.getY(), transformation);
    point.setX(coord.x);
    point.setY(coord.y);
  }

  public static void reprojectLineString(LineString lineString, ProjectionTransform transformation) {
    for (Point point : lineString.getPoints()) {
      reprojectPoint(point, transformation);
    }
  }

  public static void reprojectPolygon(Polygon polygon, ProjectionTransform transformation) {
    for (LineString lineString : polygon.getRings()) {
      reprojectLineString(lineString, transformation);
    }
  }

  public static void reprojectMultiPoint(MultiPoint multiPoint, ProjectionTransform transformation) {
    for (Point point : multiPoint.getPoints()) {
      reprojectPoint(point, transformation);
    }
  }

  public static void reprojectMultiLineString(MultiLineString multiLineString,
      ProjectionTransform transformation) {
    for (LineString lineString : multiLineString.getLineStrings()) {
      reprojectLineString(lineString, transformation);
    }
  }

  public static void reprojectMultiPolygon(MultiPolygon multiPolygon, ProjectionTransform transformation) {
    for (Polygon polygon : multiPolygon.getPolygons()) {
      reprojectPolygon(polygon, transformation);
    }
  }

  public static void reprojectGeometryCollection(GeometryCollection<Geometry> collection,
      ProjectionTransform transformation) {
    for (Geometry geometry : collection.getGeometries()) {
      reproject(geometry, transformation);
    }
  }

  public static void reprojectGeometryEnvelope(GeometryEnvelope envelope, ProjectionTransform transformation) {
    ProjCoordinate lowerLeft = reprojectCoordinate(envelope.getMinX(), envelope.getMinY(), transformation);
    ProjCoordinate upperRight = reprojectCoordinate(envelope.getMaxX(), envelope.getMaxY(), transformation);

    envelope.setMinX(lowerLeft.x);
    envelope.setMinY(lowerLeft.y);
    envelope.setMaxX(upperRight.x);
    envelope.setMaxY(upperRight.y);
  }

  public static ProjCoordinate reprojectCoordinate(Double x, Double y,
      ProjectionTransform transformation) {
    ProjCoordinate from = new ProjCoordinate(x, y);
    return transformation.transform(from);
  }
}
