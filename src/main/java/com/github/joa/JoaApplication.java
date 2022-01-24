package com.github.joa;

import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.joa.rest.exception.QueryParamExceptionHandler;
import com.github.joa.rest.resources.CollectionResource;
import com.github.joa.services.CollectionService;
import com.github.joa.services.geopackage.GeoPackageService;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ServerProperties;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

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
  public void run(final JoaConfiguration configuration, final Environment environment) {

    environment.jersey().property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
    environment.jersey().register(QueryParamExceptionHandler.class);

    // set up cors
    filters(configuration, environment);

    // set up features
    features(configuration, environment);

    // set up providers
    providers(configuration, environment);

    // set up resources
    resources(configuration, environment);
  }

  private void resources(final JoaConfiguration configuration, final Environment environment) {
    // fetch the data directory
    final String dataDirectory = configuration.getDataDirectory();

    // set up services
    final CollectionService collectionService = new GeoPackageService(dataDirectory);

    // set up resources
    final CollectionResource collectionResource = new CollectionResource(collectionService);

    environment.jersey().register(collectionResource);

    // openapi
    OpenAPI oas = new OpenAPI();
    Info info = new Info().title("JOA").description("Java based OGC-API-Features implementation.")
        .license(new License().name("MIT")).version("1.0.0")
        .termsOfService("http://example.com/terms")
        .contact(new Contact().email("sebastian.frey@outlook.com"));

    oas.info(info);
    SwaggerConfiguration oasConfig = new SwaggerConfiguration().openAPI(oas).prettyPrint(true)
        .resourcePackages(Stream.of("com.github.joa.rest").collect(Collectors.toSet()));
    environment.jersey().register(new OpenApiResource().openApiConfiguration(oasConfig));
  }

  private void filters(final JoaConfiguration configuration, final Environment environment) {
    final FilterRegistration.Dynamic cors =
        environment.servlets().addFilter("CORS", CrossOriginFilter.class);

    // Configure CORS parameters
    cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
        "X-Requested-With,Content-Type,Accept,Origin,Authorization");
    cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
        "OPTIONS,GET,PUT,POST,DELETE,HEAD");
    cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

    // Add URL mapping
    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
  }

  private void features(final JoaConfiguration configuration, final Environment environment) {
    environment.jersey().register(DeclarativeLinkingFeature.class);
  }

  private void providers(final JoaConfiguration configuration, final Environment environment) {
    environment.jersey().register(JacksonJaxbJsonProvider.class);
  }
}
