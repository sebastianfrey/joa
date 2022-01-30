package com.github.sebastianfrey.joa.resources.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Datetime;
import com.github.sebastianfrey.joa.models.FeatureQuery;
import com.github.sebastianfrey.joa.resources.annotations.ValidBbox;
import com.github.sebastianfrey.joa.resources.annotations.ValidDatetime;

/**
 * JAX-RS specific FeatureQuery implementation.
 *
 * @author sfrey
 */
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
  private Bbox bbox;

  @QueryParam("datetime")
  @ValidDatetime
  private Datetime datetime;

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
    try {
      bbox.validate();
    } catch (Exception ex) {
      return null;
    }

    // return null, when oneof minx, miny, maxx oder maxx is null
    if (bbox.getMinX() == null || bbox.getMinY() == null || bbox.getMaxX() == null
        || bbox.getMaxY() == null) {
      return null;
    }

    return bbox;
  }

  @Override
  public Datetime getDatetime() {
    try {
      datetime.validate();
    } catch (Exception ex) {
      return null;
    }

    // return null when lower or upper is null or empty
    if (datetime.getLower() == null || datetime.getUpper() == null || datetime.getLower().isEmpty()
        || datetime.getUpper().isEmpty()) {
      return null;
    }

    return datetime;
  }

  @Override
  public String getQueryString() {
    return uriInfo.getRequestUri().getQuery();
  }

  @Override
  public MultivaluedMap<String, String> getQueryParameters() {
    return uriInfo.getQueryParameters();
  }
}
