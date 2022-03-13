package com.github.sebastianfrey.joa.resources.filters;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import com.github.sebastianfrey.joa.models.Item;
import com.github.sebastianfrey.joa.models.Items;
import com.github.sebastianfrey.joa.resources.views.ItemView;
import com.github.sebastianfrey.joa.resources.views.ItemsView;
import com.github.sebastianfrey.joa.utils.CrsUtils;

/**
 * Set Content-Crs header on Items oder Item response.
 *
 * @author sfrey
 */
@Provider
@Priority(0)
public class ContentCrsResponseFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {

    Object entity = responseContext.getEntity();

    if (entity instanceof Items || entity instanceof Item || entity instanceof ItemsView
        || entity instanceof ItemView) {
      String crs = requestContext.getUriInfo().getQueryParameters().getFirst("crs");

      if (crs == null) {
        crs = CrsUtils.CRS84;
      }

      responseContext.getHeaders().add("Content-Crs", "<" + crs + ">");
    }
  }
}
