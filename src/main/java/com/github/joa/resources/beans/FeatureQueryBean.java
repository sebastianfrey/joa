package com.github.joa.resources.beans;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import com.github.joa.core.DateTime;
import com.github.joa.core.FeatureQuery;
import com.github.joa.resources.params.BboxParam;
import com.github.joa.resources.params.DateTimeParam;
import com.github.joa.resources.validators.ValidBbox;
import com.github.joa.resources.validators.ValidDateTime;

import mil.nga.sf.GeometryEnvelope;


public class FeatureQueryBean implements FeatureQuery {

  @QueryParam("limit")
  @DefaultValue("10")
  @Min(1)
  @Max(10000)
  private Integer limit;

  @QueryParam("offset")
  @DefaultValue("0")
  private Long offset;

  @QueryParam("bbox")
  @ValidBbox
  private BboxParam bbox;

  @QueryParam("datetime")
  @ValidDateTime
  private DateTimeParam datetime;

  @Override
  public Integer getLimit() {
    return limit;
  }

  @Override
  public Long getOffset() {
    return offset;
  }


  @Override
  public GeometryEnvelope getBbox() {
    return bbox.get();
  }

  @Override
  public DateTime getDateTime() {
    return datetime.get();
  }
}
