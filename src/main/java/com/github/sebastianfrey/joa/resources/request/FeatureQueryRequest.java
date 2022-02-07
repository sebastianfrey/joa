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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

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
  @Parameter(schema = @Schema(format = "form"), required = false, explode = Explode.FALSE,
      in = ParameterIn.QUERY, style = ParameterStyle.FORM)
  private Integer limit;

  @QueryParam("offset")
  @DefaultValue("0")
  private Long offset;

  @Parameter(required = false, explode = Explode.FALSE, style = ParameterStyle.FORM,
      in = ParameterIn.QUERY, array = @ArraySchema(minItems = 4, maxItems = 6))
  @QueryParam("bbox")
  @ValidBbox
  private Bbox bbox;

  @Parameter(schema = @Schema(type = "string"), style = ParameterStyle.FORM, required = false,
      explode = Explode.FALSE, in = ParameterIn.QUERY)
  @QueryParam("datetime")
  @ValidDatetime
  private Datetime datetime;

  @Override
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public FeatureQueryRequest limit(Integer limit) {
    setLimit(limit);
    return this;
  }

  @Override
  public Long getOffset() {
    return offset;
  }

  public void setOffset(Long offset) {
    this.offset = offset;
  }

  public FeatureQueryRequest offset(Long offset) {
    setOffset(offset);
    return this;
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

  public void setBbox(Bbox bbox) {
    this.bbox = bbox;
  }

  public FeatureQueryRequest bbox(Bbox bbox) {
    this.bbox = bbox;
    return this;
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

  public void setDatetime(Datetime datetime) {
    this.datetime = datetime;
  }

  public FeatureQueryRequest datetime(Datetime datetime) {
    this.datetime = datetime;
    return this;
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
