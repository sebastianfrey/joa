package com.github.sebastianfrey.joa.resources.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import com.github.sebastianfrey.joa.models.Bbox;
import com.github.sebastianfrey.joa.models.Crs;
import com.github.sebastianfrey.joa.models.Datetime;
import com.github.sebastianfrey.joa.models.ItemsQuery;
import com.github.sebastianfrey.joa.resources.annotations.SupportedCrs;
import com.github.sebastianfrey.joa.resources.annotations.ValidBbox;
import com.github.sebastianfrey.joa.resources.annotations.ValidCrs;
import com.github.sebastianfrey.joa.resources.annotations.ValidDatetime;
import com.github.sebastianfrey.joa.utils.CrsUtils;
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
public class ItemsQueryRequest extends ItemsQuery {

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

  @Parameter(schema = @Schema(format = "form"), required = false, explode = Explode.FALSE,
      in = ParameterIn.QUERY, style = ParameterStyle.FORM)
  @QueryParam("crs")
  @DefaultValue(CrsUtils.CRS84_URI)
  @ValidCrs
  @SupportedCrs
  private Crs crs;

  @Parameter(required = false, explode = Explode.FALSE, style = ParameterStyle.FORM,
      in = ParameterIn.QUERY, array = @ArraySchema(minItems = 4, maxItems = 6))
  @QueryParam("bbox")
  @ValidBbox
  private Bbox bbox;

  @Parameter(schema = @Schema(format = "form"), required = false, explode = Explode.FALSE,
      in = ParameterIn.QUERY, style = ParameterStyle.FORM)
  @QueryParam("bbox-crs")
  @DefaultValue(CrsUtils.CRS84_URI)
  @ValidCrs
  @SupportedCrs
  private Crs bboxCrs;

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

  public ItemsQueryRequest limit(Integer limit) {
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

  public ItemsQueryRequest offset(Long offset) {
    setOffset(offset);
    return this;
  }

  @Override
  public Crs getCrs() {
    if (crs == null || !crs.validate()) {
      return null;
    }

    return crs;
  }

  public void setCrs(Crs crs) {
    this.crs = crs;
  }

  public ItemsQueryRequest crs(Crs crs) {
    setCrs(crs);
    return this;
  }

  @Override
  public Bbox getBbox() {
    if (bbox == null || !bbox.validate()) {
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

  public ItemsQueryRequest bbox(Bbox bbox) {
    this.bbox = bbox;
    return this;
  }

  @Override
  public Crs getBboxCrs() {
    return bboxCrs;
  }

  public void setBboxCrs(Crs bboxCrs) {
    this.bboxCrs = bboxCrs;
  }

  public ItemsQueryRequest bboxCrs(Crs bboxCrs) {
    setBboxCrs(bboxCrs);
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

  public ItemsQueryRequest datetime(Datetime datetime) {
    this.datetime = datetime;
    return this;
  }

  @Override
  public String getQueryString() {
    if (uriInfo == null) {
      return "";
    }

    return uriInfo.getRequestUri().getQuery();
  }

  @Override
  public MultivaluedMap<String, String> getQueryParameters() {
    if (uriInfo == null) {
      return new MultivaluedHashMap<>();
    }

    return uriInfo.getQueryParameters();
  }
}
