package com.github.geoio.db;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.GeoPackageManager;

public class GeopackageService {
  private File root;

  public GeopackageService(String path) {
    root = new File(path);
  }

  public List<String> list() throws IOException {
    Set<String> fileList = new HashSet<>();

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(root.toPath())) {
      for (Path path : stream) {
        if (!Files.isDirectory(path)) {
          String fileName = path.getFileName().toString();

          if (fileName.endsWith(".gpkg")) {
            fileName = fileName.replaceAll(".gpkg", "");

            fileList.add(fileName);
          }
        }
      }
    }

    return new ArrayList<String>(fileList);
  }

  public GeoPackage open(String file) {
    File path = Paths.get(root.getAbsolutePath(), file).toFile();
    return GeoPackageManager.open(path);
  }
}
