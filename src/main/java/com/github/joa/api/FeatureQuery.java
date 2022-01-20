package com.github.joa.api;

import mil.nga.sf.GeometryEnvelope;

public interface FeatureQuery {
  public GeometryEnvelope getBbox();
  public DateTime getDateTime();
  public Long getOffset();
  public Integer getLimit();
}
