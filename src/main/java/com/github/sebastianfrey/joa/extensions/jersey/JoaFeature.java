package com.github.sebastianfrey.joa.extensions.jersey;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import com.github.sebastianfrey.joa.resources.OGCAPIServiceResource;
import com.github.sebastianfrey.joa.resources.exception.QueryParamExceptionHandler;
import com.github.sebastianfrey.joa.resources.filters.RewriteFormatQueryParamToAcceptHeaderRequestFilter;
import com.github.sebastianfrey.joa.services.OGCAPIService;
import com.github.sebastianfrey.joa.resources.filters.AlternateLinksResponseFilter;
import com.github.sebastianfrey.joa.resources.filters.ContextAwareViewResponseFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ServerProperties;

public class JoaFeature implements Feature {

  private final OGCAPIService ogcApiService;

  public JoaFeature(OGCAPIService ogcApiService) {
    this.ogcApiService = ogcApiService;
  }

  @Override
  public boolean configure(FeatureContext context) {
    // setup linking
    context.register(DeclarativeLinkingFeature.class);


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

    // setup dependency injection for CollectionService
    context.register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(ogcApiService).to(OGCAPIService.class);
      }
    });

    // set up resources
    context.register(OGCAPIServiceResource.class);

    return true;
  }
}
