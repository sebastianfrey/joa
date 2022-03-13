package com.github.sebastianfrey.joa.resources.request;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import com.github.sebastianfrey.joa.models.Crs;
import com.github.sebastianfrey.joa.models.ItemQuery;
import com.github.sebastianfrey.joa.resources.annotations.SupportedCrs;
import com.github.sebastianfrey.joa.resources.annotations.ValidCrs;
import com.github.sebastianfrey.joa.utils.CrsUtils;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Schema;

public class ItemQueryRequest extends ItemQuery {
  @Parameter(schema = @Schema(format = "form"), required = false, explode = Explode.FALSE,
      in = ParameterIn.QUERY, style = ParameterStyle.FORM)
  @QueryParam("crs")
  @DefaultValue(CrsUtils.CRS84)
  @ValidCrs
  @SupportedCrs
  private Crs crs;

  @Override
  public Crs getCrs() {
    return crs;
  }
}
