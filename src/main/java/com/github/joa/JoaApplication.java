package com.github.joa;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.joa.resources.CollectionResource;
import com.github.joa.services.CollectionService;
import com.github.joa.services.geopackage.GeoPackageCollectionService;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class JoaApplication extends Application<JoaConfiguration> {

    public static void main(final String[] args) throws Exception {
        new JoaApplication().run(args);
    }

    @Override
    public String getName() {
        return "joa";
    }

    @Override
    public void initialize(final Bootstrap<JoaConfiguration> bootstrap) {
        // multipart support
        bootstrap.addBundle(new MultiPartBundle());

        // serve assets folder
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(final JoaConfiguration configuration,
            final Environment environment) {

        // set up cors
        final FilterRegistration.Dynamic cors = environment.servlets()
                .addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
                "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM,
                "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true,
                "/*");

        // set up features
        environment.jersey().register(DeclarativeLinkingFeature.class);

        // set up providers
        environment.jersey().register(JacksonJaxbJsonProvider.class);

        // fetch the data directory
        final String dataDirectory = configuration.getDataDirectory();

        // set up services
        final CollectionService collectionService = new GeoPackageCollectionService(
                dataDirectory);

        // set up resources
        final CollectionResource collectionResource = new CollectionResource(
                collectionService);

        environment.jersey().register(collectionResource);
    }
}
