package com.github.sebastianfrey.joa.services.gpkg;

import java.util.ArrayList;
import java.util.List;
import com.github.sebastianfrey.joa.models.Datetime;
import com.github.sebastianfrey.joa.models.FeatureQuery;
import mil.nga.geopackage.db.GeoPackageDataType;
import mil.nga.geopackage.features.user.FeatureColumn;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureResultSet;

public class GeoPackageQuery {
  FeatureDao featureDao;
  FeatureQuery query;

  public GeoPackageQuery(FeatureDao featureDao, FeatureQuery query) {
    this.featureDao = featureDao;
    this.query = query;
  }

  public GeoPackageQueryResult execute() {
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

  public String where(StringBuilder whereBuilder, List<String> whereArgs) {
    buildDatetimeWhere(whereBuilder, whereArgs);

    return null;
  }

  public void buildDatetimeWhere(StringBuilder whereBuilder, List<String> whereArgs) {
    // get all datetime columns
    List<FeatureColumn> columns = featureDao.getColumns().stream().filter((column) -> {
      return column.getDataType().equals(GeoPackageDataType.DATETIME);
    }).toList();

    if (columns.isEmpty()) {
      return;
    }

    Datetime datetime = query.getDatetime();

    for (FeatureColumn column : columns) {
      whereBuilder.append(" AND (").append(column.getName());

      switch (datetime.getType()) {
        case DATETIME:
          whereBuilder.append(" =  ?");
          whereArgs.add(stringify(datetime.getLower()));
          break;

        case INTERVAL_CLOSED:
          whereBuilder.append(" BETWEEN ? AND ?");
          whereArgs.add(stringify(datetime.getLower()));
          whereArgs.add(stringify(datetime.getUpper()));
          break;

        case INTERVAL_OPEN_START:
          whereBuilder.append(" <= ?");
          whereArgs.add(stringify(datetime.getUpper()));
          break;

        case INTERVAL_OPEN_END:
          whereBuilder.append(" >= ?");
          whereArgs.add(stringify(datetime.getLower()));
          break;
      }

      whereBuilder.append(")");
    }

  }

  public String stringify(String value) {
    return /* "'" +  */value/*  + "'" */;
  }
}
