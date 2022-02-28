package com.github.sebastianfrey.joa;

import io.dropwizard.Configuration;

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

  @JsonProperty("joa")
  public OGCAPIServiceFactory getJoaServiceFactory() {
    return joaServiceFactory;
  }

  @JsonProperty("joa")
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

  public OGCAPIService getOGCAPIService() {
    return joaServiceFactory.getOGCAPIService();
  }
}
