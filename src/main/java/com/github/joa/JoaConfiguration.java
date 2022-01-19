package com.github.joa;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

public class JoaConfiguration extends Configuration {
    @NotEmpty
    private String dataDirectory;

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty
    public String getDataDirectory() {
        return dataDirectory;
    }

    @JsonProperty
    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }
}
