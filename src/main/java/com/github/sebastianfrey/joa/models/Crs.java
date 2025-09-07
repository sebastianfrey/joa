package com.github.sebastianfrey.joa.models;

import com.github.sebastianfrey.joa.utils.CrsUtils;

public class Crs {

  private final String uri;
  private final String code;
  private final String authority;

  public Crs(String crs) {
    uri = CrsUtils.parse(crs);
    code = CrsUtils.code(uri);
    authority = CrsUtils.authority(uri);
  }

  public String getUri() {
    return uri;
  }

  public String getCode() {
    return code;
  }

  public String getAuthority() {
    return authority;
  }

  public boolean validate() {
    if (authority == null) {
      return false;
    }

    if (!CrsUtils.CRS84_ID.equals(code)) {
      try {
        Long.valueOf(code);
      } catch (NumberFormatException ex) {
        return false;
      }
    }


    return true;
  }

}
