package com.github.joa.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.joa.db.GeoPackageService;

import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.extension.rtree.RTreeIndexExtension;
import mil.nga.geopackage.extension.rtree.RTreeIndexTableDao;
import mil.nga.geopackage.features.index.FeatureIndexManager;
import mil.nga.geopackage.features.index.FeatureIndexResults;
import mil.nga.geopackage.features.index.FeatureIndexType;
import mil.nga.geopackage.features.user.FeatureDao;
import mil.nga.geopackage.features.user.FeatureResultSet;
import mil.nga.geopackage.features.user.FeatureRow;
import mil.nga.geopackage.geom.GeoPackageGeometryData;
import mil.nga.geopackage.user.custom.UserCustomResultSet;
import mil.nga.geopackage.user.custom.UserCustomRow;
import mil.nga.sf.Geometry;
import mil.nga.sf.GeometryEnvelope;
import mil.nga.sf.util.GeometryEnvelopeBuilder;

/**
 *
 * @author sqlitetutorial.net
 */
public class Connect {
  /**
   * Connect to a sample database
   */
  public static void connect() throws Exception {
    String url = "jdbc:sqlite:/workspaces/joa/data/example.gpkg";

    try (Connection conn = DriverManager.getConnection(url)) {
      System.out.println("Connection to SQLite has been established.");

      try (Statement stmt = conn.createStatement()) {
        String contentsQuery = """
              SELECT
                c.table_name,
                c.data_type,
                c.identifier,
                c.description,
                c.last_change,
                c.min_x,
                c.min_y,
                c.max_x,
                c.max_y,
                c.srs_id,
                g.geometry_type_name as geo_type_name,
                g.column_name as geo_column_name
              FROM
                gpkg_contents as c
              JOIN
                gpkg_geometry_columns as g ON (c.table_name = g.table_name)
              WHERE
                data_type = 'features'
            """;

        try (ResultSet rs = stmt.executeQuery(contentsQuery)) {
          while (rs.next()) {
            String tableName = rs.getString("table_name");
            String dataType = rs.getString("data_type");
            String geoColumnName = rs.getString("geo_column_name");

            System.out.println(
                "{ table_name=" + tableName + ", data_type=" + dataType + ", geoColumnName=" + geoColumnName + " }");
          }
        }

        String point1Query = """
              SELECT
                ST_AsWKT(geometry) as geometry
              FROM
                point1
              LIMIT 1
            """;

        try (ResultSet rs = stmt.executeQuery(point1Query)) {
          while (rs.next()) {
            String wkt = rs.getString("geometry");
            System.out.println("wkt=" + wkt);
          }
        }
      }
    }
  }

  public static void connect2() {
    GeoPackageService geopackageService = new GeoPackageService("/workspaces/joa/data/");

    FeatureIndexManager indexManager = null;
    FeatureIndexResults results = null;

    try (GeoPackage geoPackage = geopackageService.open("pot_WEG_Luebz_linie.gpkg")) {
      indexManager = new FeatureIndexManager(geoPackage, "pot_WEG_Luebz_linie");
      if (!indexManager.isIndexed()) {
        indexManager.setIndexLocation(FeatureIndexType.RTREE);
        indexManager.index();
      }

      Double minX = 33302120.0;
      Double minY = 5923548.0;
      Double maxX = 33303206.0;
      Double maxY = 5925394.0;

      RTreeIndexTableDao rTreeIndexTableDao = indexManager.getRTreeIndexTableDao();

      StringBuilder whereBuilder = new StringBuilder();

      whereBuilder.append(rTreeIndexTableDao.buildWhere(RTreeIndexExtension.COLUMN_MIN_X, minX, "<="));
      whereBuilder.append(" AND ");
      whereBuilder.append(rTreeIndexTableDao.buildWhere(RTreeIndexExtension.COLUMN_MIN_Y, minY, "<="));
      whereBuilder.append(" AND ");
      whereBuilder.append(rTreeIndexTableDao.buildWhere(RTreeIndexExtension.COLUMN_MAX_X, maxX, ">="));
      whereBuilder.append(" AND ");
      whereBuilder.append(rTreeIndexTableDao.buildWhere(RTreeIndexExtension.COLUMN_MAX_Y, maxY, ">="));

      String whereBounds = whereBuilder.toString();

      Double tolerance = rTreeIndexTableDao.getTolerance();

      minX -= tolerance;
      maxX += tolerance;
      minY -= tolerance;
      maxY += tolerance;

      String[] whereArgs = rTreeIndexTableDao.buildWhereArgs(new Object[] { maxX, maxY, minX, minY });

      String where = rTreeIndexTableDao.getFeatureDao().buildWhereIn(rTreeIndexTableDao.queryIdsSQL(whereBounds), null);

      FeatureResultSet featureResultSet = rTreeIndexTableDao.getFeatureDao().queryForChunk(where, whereArgs, "fid", 5, 0);

      int count = 0;
      try {
        while (featureResultSet.moveToNext()) {
          FeatureRow featureRow = featureResultSet.getRow();
          GeoPackageGeometryData geometryData = featureRow.getGeometry();
          if (geometryData != null && !geometryData.isEmpty()) {
            count++;
          }
        }
      } finally {
        featureResultSet.close();
      }
      System.out.println("count=" + count);
    } finally {
      if (results != null) {
        results.close();
      }
      if (indexManager != null) {
        indexManager.close();
      }
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
    connect2();
  }
}