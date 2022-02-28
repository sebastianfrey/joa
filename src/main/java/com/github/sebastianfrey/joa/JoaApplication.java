package com.github.sebastianfrey.joa;

import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.sebastianfrey.joa.extensions.jackson.LinkDeserializer;
import com.github.sebastianfrey.joa.extensions.jackson.LinkSerializer;
import com.github.sebastianfrey.joa.resources.OGCAPIServiceResource;
import com.github.sebastianfrey.joa.services.OGCAPIService;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
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
    bootstrap.addBundle(new AssetsBundle("/assets", "/assets", "index.html"));

    // html views
    bootstrap.addBundle(new ViewBundle<JoaConfiguration>() {
      @Override
      public Map<String, Map<String, String>> getViewConfiguration(JoaConfiguration config) {
        return config.getViewRendererConfiguration();
      }
    });
  }

  @Override
  public void run(final JoaConfiguration configuration, final Environment environment) {
    environment.getApplicationContext().getMimeTypes().addMimeMapping("mjs", "text/javascript");
    // set up features
    linking(configuration, environment);

    // set up providers
    jackson(configuration, environment);

    // set up cors
    cors(configuration, environment);

    // set up resources
    resources(configuration, environment);

    // set up openapi
    openapi(configuration, environment);
  }

  private void resources(final JoaConfiguration configuration, final Environment environment) {
    environment.jersey().register(JoaFeature.class);

    // fetch the service
    final OGCAPIService ogcApiService = configuration.getOGCAPIService();

    // setup dependency injection for CollectionService
    environment.jersey().register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(ogcApiService).to(OGCAPIService.class);
      }
    });

    // set up resources
    environment.jersey().register(OGCAPIServiceResource.class);
  }

  private void cors(final JoaConfiguration configuration, final Environment environment) {
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

  private void linking(final JoaConfiguration configuration, final Environment environment) {
    environment.jersey().register(DeclarativeLinkingFeature.class);
  }

  private void jackson(final JoaConfiguration configuration, final Environment environment) {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Link.class, new LinkSerializer());
    module.addDeserializer(Link.class, new LinkDeserializer());
    environment.getObjectMapper().registerModule(module);
  }

  private void openapi(final JoaConfiguration configuration, final Environment environment) {
    // openapi
    Info info = new Info().title("JOA")
        .description("Java based OGC-API-Features implementation.")
        .license(new License().name("MIT"))
        .version("1.0.0")
        .termsOfService("http://example.com/terms")
        .contact(new Contact().email("sebastian.frey@outlook.com"));

    OpenAPI oas = new OpenAPI().info(info);
    SwaggerConfiguration oasConfig = new SwaggerConfiguration().openAPI(oas)
        .prettyPrint(true)
        .resourcePackages(
            Stream.of("com.github.sebastianfrey.joa.resources").collect(Collectors.toSet()));
    environment.jersey().register(new OpenApiResource().openApiConfiguration(oasConfig));
  }
}
