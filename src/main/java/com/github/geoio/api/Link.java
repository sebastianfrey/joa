package com.github.geoio.api;

public class Link {
  private String href;
  private String rel;
  private String type;
  private String title;
  private Long length;

  public String getHref() {
    return href;
  }
  public void setHref(String href) {
    this.href = href;
  }
  public String getRel() {
    return rel;
  }
  public void setRel(String rel) {
    this.rel = rel;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public Long getLength() {
    return length;
  }
  public void setLength(Long length) {
    this.length = length;
  }


}
