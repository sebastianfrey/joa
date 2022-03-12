package com.github.sebastianfrey.joa.services.gpkg;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Crs;
import com.github.sebastianfrey.joa.models.Datetime;
import com.github.sebastianfrey.joa.models.ItemsQuery;
import com.github.sebastianfrey.joa.utils.ProjectionUtils;
import org.locationtech.proj4j.ProjCoordinate;
import mil.nga.geopackage.db.GeoPackageDataType;
import mil.nga.geopackage.features.user.FeatureColumn;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureResultSet;
import mil.nga.proj.ProjectionTransform;
import mil.nga.sf.geojson.Polygon;
import mil.nga.sf.geojson.Position;

public class GeoPackageQuery {
  private final static ObjectMapper objectMapper = new ObjectMapper();
  FeatureDao featureDao;
  ItemsQuery query;

  public GeoPackageQuery(FeatureDao featureDao, ItemsQuery query) {
    this.featureDao = featureDao;
    this.query = query;
  }

  public GeoPackageQueryResult execute() throws Exception {
    String orderBy = orderBy();
    Integer limit = limit();
    Long offset = offset();

    StringBuilder whereBuilder = new StringBuilder("1=1");
    List<String> whereArgs = new ArrayList<>();

    where(whereBuilder, whereArgs);

    String where = whereBuilder.toString();
    String[] args = whereArgs.toArray(new String[] {});

    FeatureResultSet results = featureDao.queryForChunk(where, args, orderBy, limit, offset);
    Integer count = featureDao.count(where, args);

    return new GeoPackageQueryResult(count.longValue(), results);
  }

  private Long offset() {
    return query.getOffset();
  }

  public Integer limit() {
    return query.getLimit();
  }

  public String orderBy() {
    return featureDao.getIdColumnName();
  }

  public String where(StringBuilder whereBuilder, List<String> whereArgs) throws Exception {
    buildDatetimeWhere(whereBuilder, whereArgs);
    buildQueryWhere(whereBuilder, whereArgs);
    buildBboxWhere(whereBuilder, whereArgs);

    return null;
  }

  public void buildDatetimeWhere(StringBuilder whereBuilder, List<String> whereArgs) {
    // verify datetime
    Datetime datetime = query.getDatetime();
    if (datetime == null) {
      return;
    }

    // get all datetime columns
    List<FeatureColumn> columns = featureDao.getColumns().stream().filter((column) -> {
      return column.getDataType().equals(GeoPackageDataType.DATETIME);
    }).toList();

    if (columns.isEmpty()) {
      return;
    }

    for (FeatureColumn column : columns) {
      whereBuilder.append(" AND (").append(column.getName());

      switch (datetime.getType()) {
        case DATETIME:
          whereBuilder.append(" =  ?");
          whereArgs.add(datetime.getLower());
          break;

        case INTERVAL_CLOSED:
          whereBuilder.append(" BETWEEN ? AND ?");
          whereArgs.add(datetime.getLower());
          whereArgs.add(datetime.getUpper());
          break;

        case INTERVAL_OPEN_START:
          whereBuilder.append(" <= ?");
          whereArgs.add(datetime.getUpper());
          break;

        case INTERVAL_OPEN_END:
          whereBuilder.append(" >= ?");
          whereArgs.add(datetime.getLower());
          break;
      }

      whereBuilder.append(")");
    }
  }


  public void buildQueryWhere(StringBuilder whereBuilder, List<String> whereArgs) {
    MultivaluedMap<String, String> parameters = query.getQueryParameters();
    if (parameters.isEmpty()) {
      return;
    }

    List<String> columnNames = List.of(featureDao.getColumnNames());

    parameters.forEach((columnName, values) -> {
      if (!columnNames.contains(columnName)
          || ItemsQuery.RESERVED_QUERY_PARAMS.contains(columnName)) {
        return;
      }

      whereBuilder.append(" AND (");

      for (int i = 0; i < values.size(); i++) {
        if (i > 0) {
          whereBuilder.append(" OR ");
        }

        whereBuilder.append(columnName).append(" = ?");
        whereArgs.add(values.get(i));
      }

      whereBuilder.append(")");
    });
  }

  public void buildBboxWhere(StringBuilder whereBuilder, List<String> whereArgs) throws Exception {
    // verify bbox
    Bbox bbox = query.getBbox();
    if (bbox == null) {
      return;
    }

    Crs bboxCrs = query.getBboxCrs();

    String geometryColumn = featureDao.getGeometryColumnName();
    String polygon = objectMapper.writeValueAsString(toPolygon(bbox, bboxCrs));

    whereBuilder.append(" AND (ST_Intersects(")
        .append(geometryColumn)
        .append(", GeomFromGeoJSON(?)))");
    whereArgs.add(polygon);
  }

  private Polygon toPolygon(Bbox bbox, Crs bboxCrs) {
    Double minX = bbox.getMinX();
    Double minY = bbox.getMinY();
    // Double minZ = bbox.getMinZ();
    Double maxX = bbox.getMaxX();
    Double maxY = bbox.getMaxY();
    // Double maxZ = bbox.getMaxZ();

    if (bboxCrs != null) {
      ProjectionTransform transformation = ProjectionUtils.getTransformation(bboxCrs, featureDao);

      if (transformation != null) {
        ProjCoordinate lowerLeft = ProjectionUtils.reprojectCoordinate(minX, minY, transformation);
        ProjCoordinate upperRight = ProjectionUtils.reprojectCoordinate(maxX, maxY, transformation);

        minX = lowerLeft.x;
        minY = lowerLeft.y;
        maxX = upperRight.x;
        maxY = upperRight.y;
      }
    }

    Polygon polygon = new Polygon();

    Position lowerLeft = new Position(minX, minY);
    Position upperLeft = new Position(minX, maxY);
    Position upperRight = new Position(maxX, maxY);
    Position lowerRight = new Position(maxX, minY);

    List<List<Position>> coordinates =
        List.of(List.of(lowerLeft, upperLeft, upperRight, lowerRight, lowerLeft));

    polygon.setCoordinates(coordinates);

    return polygon;
  }
}
