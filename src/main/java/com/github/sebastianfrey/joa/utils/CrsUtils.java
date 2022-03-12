package com.github.sebastianfrey.joa.utils;

import mil.nga.proj.ProjectionConstants;

public class CrsUtils {

  public static final String CRS84 = "CRS84";
  public static final String CRS84_URI = "http://www.opengis.net/def/crs/OGC/1.3/" + CRS84;

  public static final String EPSG_BASE_URI = "http://www.opengis.net/def/crs/EPSG/0/";

  public static String crs84() {
    return CRS84_URI;
  }

  public static String parse(String crs) {
    if ("4326".equals(crs) || "CRS84".equals(crs)) {
      return CRS84_URI;
    }

    if (crs.equals(CRS84_URI)) {
      return crs;
    }

    if (crs.startsWith(EPSG_BASE_URI)) {
      return crs;
    }

    return EPSG_BASE_URI + crs;
  }

  public static String parse(Long crsId) {
    return parse(crsId.toString());
  }

  public static String parse(Integer crsId) {
    return parse(crsId.toString());
  }

  public static String authority(String crsUri) {
    if (crsUri == null) {
      return null;
    }
    if (crsUri.equals(CRS84_URI)) {
      return ProjectionConstants.AUTHORITY_OGC;
    } else if (crsUri.startsWith(EPSG_BASE_URI)) {
      return ProjectionConstants.AUTHORITY_EPSG;
    }

    return null;
  }

  public static String code(String crsUri) {
    if (crsUri == null) {
      return null;
    }
    if (crsUri.startsWith(CRS84_URI)) {
      return CRS84;
    } else if (crsUri.startsWith(EPSG_BASE_URI)) {
      return crsUri.replace("http://www.opengis.net/def/crs/EPSG/0/", "");
    }

    return null;
  }
}
