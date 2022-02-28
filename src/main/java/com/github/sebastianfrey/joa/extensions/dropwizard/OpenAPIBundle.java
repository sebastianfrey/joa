package com.github.sebastianfrey.joa.extensions.dropwizard;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.github.sebastianfrey.joa.JoaConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;

public class OpenAPIBundle implements ConfiguredBundle<JoaConfiguration> {

  @Override
  public void run(JoaConfiguration configuration, Environment environment) throws Exception {
    OpenAPI oas = configuration.getOpenAPIConfiguration();

    SwaggerConfiguration oasConfig = new SwaggerConfiguration().openAPI(oas)
        .prettyPrint(true)
        .resourcePackages(
            Stream.of("com.github.sebastianfrey.joa.resources").collect(Collectors.toSet()));

    environment.jersey().register(new OpenApiResource().openApiConfiguration(oasConfig));  }

}
