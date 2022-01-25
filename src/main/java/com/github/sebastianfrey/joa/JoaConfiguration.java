package com.github.sebastianfrey.joa;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

public class JoaConfiguration extends Configuration {
    @NotEmpty
    private String dataDirectory;

    @JsonProperty
    public String getDataDirectory() {
        return dataDirectory;
    }

    @JsonProperty
    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }
}
