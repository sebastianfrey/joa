package com.github.geoio;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

public class GeoIOConfiguration extends Configuration {
    @NotEmpty
    private String collectionDb;

    @JsonProperty
    public String getCollectionDb() {
        return collectionDb;
    }

    @JsonProperty
    public void setCollectionDb(String collectionDb) {
        this.collectionDb = collectionDb;
    }
}
