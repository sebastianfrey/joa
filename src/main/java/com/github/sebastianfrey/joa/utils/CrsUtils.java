package com.github.sebastianfrey.joa.utils;

import mil.nga.proj.ProjectionConstants;

public class CrsUtils {

  public static final String CRS84_ID = "CRS84";
  public static final String CRS84 = "http://www.opengis.net/def/crs/OGC/1.3/" + CRS84_ID;

  public static final String EPSG_BASE = "http://www.opengis.net/def/crs/EPSG/0/";

  public static String crs84() {
    return CRS84;
  }

  public static String parse(String crs) {
    if ("4326".equals(crs) || "CRS84".equals(crs)) {
      return CRS84;
    }

    if (crs.equals(CRS84)) {
      return crs;
    }

    if (crs.startsWith(EPSG_BASE)) {
      return crs;
    }

    return EPSG_BASE + crs;
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
    if (crsUri.equals(CRS84)) {
      return ProjectionConstants.AUTHORITY_OGC;
    } else if (crsUri.startsWith(EPSG_BASE)) {
      return ProjectionConstants.AUTHORITY_EPSG;
    }

    return null;
  }

  public static String code(String crsUri) {
    if (crsUri == null) {
      return null;
    }
    if (crsUri.startsWith(CRS84)) {
      return CRS84_ID;
    } else if (crsUri.startsWith(EPSG_BASE)) {
      return crsUri.replace("http://www.opengis.net/def/crs/EPSG/0/", "");
    }

    return null;
  }
}
