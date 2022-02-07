package com.github.sebastianfrey.joa.services.gpkg;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
/* import com.fasterxml.jackson.databind.ObjectMapper; */
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Datetime;
import com.github.sebastianfrey.joa.models.FeatureQuery;
import mil.nga.geopackage.db.GeoPackageDataType;
import mil.nga.geopackage.features.index.FeatureIndexManager;
import mil.nga.geopackage.features.index.FeatureIndexResults;
import mil.nga.geopackage.features.user.FeatureColumn;
import mil.nga.geopackage.features.user.FeaturePaginatedResults;
import mil.nga.sf.GeometryEnvelope;
/*
import mil.nga.sf.geojson.Polygon;
import mil.nga.sf.geojson.Position;
*/

public class GeoPackageQuery {
/* private final static ObjectMapper objectMapper = new ObjectMapper(); */
  FeatureIndexManager indexer;
  FeatureQuery query;

  public GeoPackageQuery(FeatureIndexManager indexer, FeatureQuery query) {
    this.indexer = indexer;
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

    Bbox bbox = query.getBbox();
    GeometryEnvelope envelope = null;
    if (query.getBbox() != null) {
      envelope = new GeometryEnvelope(bbox.getMinX(), bbox.getMinY(), bbox.getMinZ(),
          bbox.getMaxX(), bbox.getMaxY(), bbox.getMaxZ());
    }

    FeatureIndexResults indexResults = indexer.queryForChunk(envelope, where, args, orderBy, limit, offset);
    FeaturePaginatedResults paginatedResults = indexer.paginate(indexResults);
    Long count = indexer.count(where, args);

    return new GeoPackageQueryResult(count, paginatedResults);
  }

  private Long offset() {
    return query.getOffset();
  }

  public Integer limit() {
    return query.getLimit();
  }

  public String orderBy() {
    return indexer.getFeatureDao().getIdColumnName();
  }

  public String where(StringBuilder whereBuilder, List<String> whereArgs) throws Exception {
    buildDatetimeWhere(whereBuilder, whereArgs);
    buildQueryWhere(whereBuilder, whereArgs);
    /* buildBboxWhere(whereBuilder, whereArgs); */

    return null;
  }

  public void buildDatetimeWhere(StringBuilder whereBuilder, List<String> whereArgs) {
    // verify datetime
    Datetime datetime = query.getDatetime();
    if (datetime == null) {
      return;
    }

    // get all datetime columns
    List<FeatureColumn> columns = indexer.getFeatureDao().getColumns().stream().filter((column) -> {
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

    List<String> columnNames = List.of(indexer.getFeatureDao().getColumnNames());

    parameters.forEach((columnName, values) -> {
      if (!columnNames.contains(columnName)
          || FeatureQuery.RESERVED_QUERY_PARAMS.contains(columnName)) {
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

/*   public void buildBboxWhere(StringBuilder whereBuilder, List<String> whereArgs) throws Exception {
    // verify bbox
    Bbox bbox = query.getBbox();
    if (bbox == null) {
      return;
    }

    String geometryColumn = indexer.getFeatureDao().getGeometryColumnName();
    String polygon = objectMapper.writeValueAsString(toPolygon(bbox));

    whereBuilder.append(" AND (ST_Intersects(")
        .append(geometryColumn)
        .append(", GeomFromGeoJSON(?)))");
    whereArgs.add(polygon);
  }

  private Polygon toPolygon(Bbox bbox) {
    Double minX = bbox.getMinX();
    Double minY = bbox.getMinY();
    // Double minZ = bbox.getMinZ();
    Double maxX = bbox.getMaxX();
    Double maxY = bbox.getMaxY();
    // Double maxZ = bbox.getMaxZ();

    Polygon polygon = new Polygon();

    Position lowerLeft = new Position(minX, minY);
    Position upperLeft = new Position(minX, maxY);
    Position upperRight = new Position(maxX, maxY);
    Position lowerRight = new Position(maxX, minY);

    List<List<Position>> coordinates =
        List.of(List.of(lowerLeft, upperLeft, upperRight, lowerRight, lowerLeft));

    polygon.setCoordinates(coordinates);

    return polygon;
  } */
}
