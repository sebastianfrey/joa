package com.github.sebastianfrey.joa.models;


public abstract class FeatureQuery {
  public abstract String getQueryString();
  public abstract Bbox getBbox();
  public abstract Datetime getDatetime();
  public abstract Long getOffset();
  public abstract Integer getLimit();
}
