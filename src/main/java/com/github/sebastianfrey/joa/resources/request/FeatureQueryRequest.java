package com.github.sebastianfrey.joa.resources.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Datetime;
import com.github.sebastianfrey.joa.models.FeatureQuery;
import com.github.sebastianfrey.joa.resources.params.BboxParam;
import com.github.sebastianfrey.joa.resources.params.DatetimeParam;
import com.github.sebastianfrey.joa.resources.validators.ValidBbox;
import com.github.sebastianfrey.joa.resources.validators.ValidDatetime;


public class FeatureQueryRequest extends FeatureQuery {

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
  @ValidDatetime
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
  public Bbox getBbox() {
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
