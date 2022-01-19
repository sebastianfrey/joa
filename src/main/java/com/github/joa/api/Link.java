package com.github.joa.api;

public class Link {
  private String href;
  private String rel;
  private String type;
  private String title;
  private Long length;

  public static class LinkBuilder {
    private String href;
    private String rel;
    private String type;
    private String title;
    private Long length;

    public LinkBuilder withHref(String href) {
      this.href = href;
      return this;
    }

    public LinkBuilder withRel(String rel) {
      this.rel = rel;
      return this;
    }

    public LinkBuilder withType(String type) {
      this.type = type;
      return this;
    }

    public LinkBuilder withTitle(String title) {
      this.title = title;
      return this;
    }

    public LinkBuilder withLength(Long length) {
      this.length = length;
      return this;
    }

    public Link build() {
      return new Link(href, rel, type, title, length);
    }
  }

  public static LinkBuilder builder() {
    return new LinkBuilder();
  }

  Link(String href, String rel, String type, String title, Long length) {
    this.href = href;
    this.rel = rel;
    this.type = type;
    this.title = title;
    this.length = length;
  }

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
