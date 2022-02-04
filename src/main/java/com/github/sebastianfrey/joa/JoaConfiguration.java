package com.github.sebastianfrey.joa;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.sebastianfrey.joa.configuration.OGCApiServiceFactory;
import com.github.sebastianfrey.joa.services.OGCApiService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class JoaConfiguration extends Configuration {
    @Valid
    @NotNull
    private OGCApiServiceFactory joaServiceFactory = new OGCApiServiceFactory();

    @JsonProperty("joa")
    public OGCApiServiceFactory getJoaServiceFactory() {
        return joaServiceFactory;
    }

    @JsonProperty("joa")
    public void setJoaServiceFactory(OGCApiServiceFactory joaServiceFactory) {
        this.joaServiceFactory = joaServiceFactory;
    }

    public OGCApiService<?, ?> getOGCApiService() {
        return joaServiceFactory.getOGCApiService();
    }
}
