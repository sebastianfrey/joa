package com.github.sebastianfrey.joa;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.sebastianfrey.joa.configuration.FeatureServiceFactory;
import com.github.sebastianfrey.joa.services.FeatureService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class JoaConfiguration extends Configuration {
    @Valid
    @NotNull
    private FeatureServiceFactory joaServiceFactory = new FeatureServiceFactory();

    @JsonProperty("joa")
    public FeatureServiceFactory getJoaServiceFactory() {
        return joaServiceFactory;
    }

    @JsonProperty("joa")
    public void setJoaServiceFactory(FeatureServiceFactory joaServiceFactory) {
        this.joaServiceFactory = joaServiceFactory;
    }

    public FeatureService<?, ?> getFeatureService() {
        return joaServiceFactory.getFeatureService();
    }
}
