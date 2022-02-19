package com.github.sebastianfrey.joa.resources.filters;

import java.io.IOException;
import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import com.github.sebastianfrey.joa.resources.views.ContextAwareView;

@Provider
@Priority(0)
public class ContextAwareViewResponseFilter implements ContainerResponseFilter {

  @Context
  HttpServletRequest request;

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    Object entity = responseContext.getEntity();

    if (entity != null && entity instanceof ContextAwareView) {
      ((ContextAwareView) entity).setRequestContext(requestContext, request);
    }
  }
}
