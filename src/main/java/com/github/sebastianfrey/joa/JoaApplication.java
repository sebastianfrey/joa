package com.github.sebastianfrey.joa;

import java.util.Map;
import com.github.sebastianfrey.joa.extensions.dropwizard.CorsBundle;
import com.github.sebastianfrey.joa.extensions.dropwizard.JacksonBundle;
import com.github.sebastianfrey.joa.extensions.dropwizard.OpenAPIBundle;
import com.github.sebastianfrey.joa.extensions.jersey.JoaFeature;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

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

    // cors support
    bootstrap.addBundle(new CorsBundle());

    // jackson extensions
    bootstrap.addBundle(new JacksonBundle());

    // openapi support
    bootstrap.addBundle(new OpenAPIBundle());

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
    // register .mjs as text/javascript
    environment.getApplicationContext().getMimeTypes().addMimeMapping("mjs", "text/javascript");

    // miscellaneous configurations
    environment.jersey().register(new JoaFeature(configuration.getOGCAPIService()));
  }
}
