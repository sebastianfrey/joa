package com.github.joa.resources.beans;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import com.github.joa.api.FeatureQuery;
import com.github.joa.resources.params.DoubleListParam;
import com.github.joa.resources.validators.Envelope;


public class FeatureQueryBean implements FeatureQuery {

  @QueryParam("bbox")
  @Envelope
  private DoubleListParam bbox;

  @QueryParam("limit")
  @DefaultValue("10")
  @Min(1)
  @Max(10000)
  private Integer limit;

  @QueryParam("offset")
  @DefaultValue("0")
  private Long offset;

  @Override
  public List<Double> getBbox() {
    return bbox.get();
  }

  @Override
  public Integer getLimit() {
    return limit;
  }

  @Override
  public Long getOffset() {
    return offset;
  }

}
