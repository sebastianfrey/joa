package com.github.geoio.db;

import java.io.File;

import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.GeoPackageManager;

public class GeopackageDatabase {
  private File file;

  public GeopackageDatabase(String path) {
    this.file = new File(path);
  }

  public GeoPackage open() {
    return GeoPackageManager.open(file);
  }
}
