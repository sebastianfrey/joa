package com.github.joa.rest.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.github.joa.core.Datetime;
import com.github.joa.core.FeatureQuery;
import com.github.joa.rest.params.BboxParam;
import com.github.joa.rest.params.DatetimeParam;
import com.github.joa.rest.validators.ValidBbox;
import com.github.joa.rest.validators.ValidDateTime;
import mil.nga.sf.GeometryEnvelope;


public class FeatureQueryRequest implements FeatureQuery {

  @Context
  UriInfo uriInfo;

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
  private DatetimeParam datetime;

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
  public Datetime getDatetime() {
    return datetime.get();
  }

  @Override
  public String getQueryString() {
    return uriInfo.getRequestUri().getQuery();
  }
}
