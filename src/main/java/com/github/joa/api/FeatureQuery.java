package com.github.joa.api;

import java.util.List;

public interface FeatureQuery {
  public List<Double> getBbox();
  public Long getOffset();
  public Integer getLimit();
}
