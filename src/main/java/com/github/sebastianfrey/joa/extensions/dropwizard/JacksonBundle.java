package com.github.sebastianfrey.joa.extensions.dropwizard;

import javax.ws.rs.core.Link;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.sebastianfrey.joa.extensions.jackson.LinkDeserializer;
import com.github.sebastianfrey.joa.extensions.jackson.LinkSerializer;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Environment;

public class JacksonBundle implements ConfiguredBundle<Configuration> {

  @Override
  public void run(Configuration configuration, Environment environment) throws Exception {
    // register custom link serializer and deserializer
    SimpleModule module = new SimpleModule();

    module.addSerializer(Link.class, new LinkSerializer());
    module.addDeserializer(Link.class, new LinkDeserializer());

    environment.getObjectMapper().registerModule(module);
  }

}
