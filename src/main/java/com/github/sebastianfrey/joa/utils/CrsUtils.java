package com.github.sebastianfrey.joa.utils;

public class CrsUtils {
  public static final String CRS84_URI = "http://www.opengis.net/def/crs/OGC/1.3/CRS84";

  public static final String BASE_URI = "http://www.opengis.net/def/crs/EPSG/0/";

  public static String crs84() {
    return CRS84_URI;
  }

  public static String epsg(String srsId) {
    if ("4326".equals(srsId)) {
      return CRS84_URI;
    }

    return BASE_URI + srsId;
  }

  public static String epsg(Long srsId) {
    return epsg(srsId.toString());
  }

  public static String epsg(Integer srsId) {
    return epsg(srsId.toString());
  }
}
