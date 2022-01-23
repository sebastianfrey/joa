package com.github.joa.core;

import mil.nga.sf.GeometryEnvelope;

public interface FeatureQuery {
  public String getQueryString();
  public GeometryEnvelope getBbox();
  public DateTime getDateTime();
  public Long getOffset();
  public Integer getLimit();
}
