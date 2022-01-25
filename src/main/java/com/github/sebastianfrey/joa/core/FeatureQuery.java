package com.github.sebastianfrey.joa.core;

import mil.nga.sf.GeometryEnvelope;

public interface FeatureQuery {
  public String getQueryString();
  public GeometryEnvelope getBbox();
  public Datetime getDatetime();
  public Long getOffset();
  public Integer getLimit();
}
