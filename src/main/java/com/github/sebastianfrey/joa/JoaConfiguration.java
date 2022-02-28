package com.github.sebastianfrey.joa;

import io.dropwizard.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.sebastianfrey.joa.configuration.OGCAPIServiceFactory;
import com.github.sebastianfrey.joa.services.OGCAPIService;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class JoaConfiguration extends Configuration {
  @Valid
  @NotNull
  private OGCAPIServiceFactory joaServiceFactory = new OGCAPIServiceFactory();

  @NotNull
  private Map<String, Map<String, String>> viewRendererConfiguration;

  @NotNull
  private OpenAPI openAPIConfiguration;

  @JsonProperty("service")
  public OGCAPIServiceFactory getJoaServiceFactory() {
    return joaServiceFactory;
  }

  @JsonProperty("service")
  public void setJoaServiceFactory(OGCAPIServiceFactory joaServiceFactory) {
    this.joaServiceFactory = joaServiceFactory;
  }

  @JsonProperty("views")
  public Map<String, Map<String, String>> getViewRendererConfiguration() {
    return viewRendererConfiguration;
  }

  @JsonProperty("views")
  public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
    this.viewRendererConfiguration = viewRendererConfiguration;
  }

  @JsonProperty("openapi")
  public OpenAPI getOpenAPIConfiguration() {
    return openAPIConfiguration;
  }

  @JsonProperty("openapi")
  public void setOpenAPIConfiguration(OpenAPI openAPIConfiguration) {
    this.openAPIConfiguration = openAPIConfiguration;
  }

  public OGCAPIService getOGCAPIService() {
    return joaServiceFactory.getOGCAPIService();
  }
}
