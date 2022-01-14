package com.github.geoio;

import com.github.geoio.resources.CollectionResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class GeoIOApplication extends Application<GeoIOConfiguration> {

    public static void main(final String[] args) throws Exception {
        new GeoIOApplication().run(args);
    }

    @Override
    public String getName() {
        return "geoio";
    }

    @Override
    public void initialize(final Bootstrap<GeoIOConfiguration> bootstrap) {
        // multipart support
        bootstrap.addBundle(new MultiPartBundle());

        // serve assets folder
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(final GeoIOConfiguration configuration,
                    final Environment environment) {
        final CollectionResource collectionsResource = new CollectionResource();

        environment.jersey().register(collectionsResource);
    }

}
