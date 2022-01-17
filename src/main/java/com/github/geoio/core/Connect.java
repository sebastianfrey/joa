package com.github.geoio.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sqlitetutorial.net
 */
public class Connect {
  /**
   * Connect to a sample database
   */
  public static void connect() throws Exception {
    String url = "jdbc:sqlite:/workspaces/geoio/data/example.gpkg";

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

            System.out.println("{ table_name=" + tableName + ", data_type=" + dataType + ", geoColumnName=" + geoColumnName + " }");
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

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
    connect();
  }
}