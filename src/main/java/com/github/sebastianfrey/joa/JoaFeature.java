package com.github.sebastianfrey.joa;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import com.github.sebastianfrey.joa.resources.exception.QueryParamExceptionHandler;
import com.github.sebastianfrey.joa.resources.filters.RewriteFormatQueryParamToAcceptHeaderRequestFilter;
import com.github.sebastianfrey.joa.resources.filters.AlternateLinksResponseFilter;
import com.github.sebastianfrey.joa.resources.filters.ContextAwareViewResponseFilter;
import org.glassfish.jersey.server.ServerProperties;

public class JoaFeature implements Feature {

  @Override
  public boolean configure(FeatureContext context) {
     // include body for 4xx and 5xx
    context.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);

     // return 400 for QueryParamExceptions
    context.register(QueryParamExceptionHandler.class);

     // rewrite format query param as accept header
    context.register(RewriteFormatQueryParamToAcceptHeaderRequestFilter.class);

     // transform linkable entities
    context.register(AlternateLinksResponseFilter.class);

    // context access for Freemarker templates
    context.register(ContextAwareViewResponseFilter.class);

    return true;
  }

}
