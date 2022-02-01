package com.github.sebastianfrey.joa.resources;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.sebastianfrey.joa.extensions.jackson.LinkDeserializer;
import com.github.sebastianfrey.joa.extensions.jackson.LinkSerializer;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.Service;
import com.github.sebastianfrey.joa.models.Services;
import com.github.sebastianfrey.joa.services.FeatureService;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import javax.ws.rs.core.Link;

@ExtendWith(DropwizardExtensionsSupport.class)
public class FeatureServiceResourceTest {

  @SuppressWarnings("unchecked")
  private static final FeatureService<Object, Object> DAO = mock(FeatureService.class);

  private static final ResourceExtension EXT = ResourceExtension.builder()
      .addProvider(MultiPartFeature.class)
      .addProvider(DeclarativeLinkingFeature.class)
      .addResource(new FeatureServiceResource(DAO))
      .build();

  static {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Link.class, new LinkSerializer());
    module.addDeserializer(Link.class, new LinkDeserializer());
    EXT.getObjectMapper().registerModule(module);
  }

  private Services services;
  private Service service;

  @BeforeEach
  void setup() {
    service = new Service().serviceId("service1")
        .title("service1 title")
        .description("service1 description");

    services = new Services().service(service);
  }

  @AfterEach
  @SuppressWarnings("unchecked")
  void tearDown() {
    reset(DAO);
  }

  @Test
  public void should_return_services() {
    when(DAO.getServices()).thenReturn(services);

    Services found = EXT.target("/").request().get(Services.class);

    assertThat(found.getServices()).satisfies(($services) -> {
      // there must be at least one service
      assertThat($services).isNotEmpty();

      // assert first service
      assertThat($services.get(0)).satisfies(($service) -> {

        // assert service properties
        assertThat($service.getServiceId()).isEqualTo(service.getServiceId());
        assertThat($service.getTitle()).isEqualTo(service.getTitle());
        assertThat($service.getDescription()).isEqualTo(service.getDescription());

        // assert service links
        assertThat($service.getLinks()).satisfies((links) -> {
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SELF));
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.CONFORMANCE));
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.SERVICE_DESC));
          assertThat(links).anyMatch((link) -> link.getRel().equals(Linkable.DATA));
        });
      });
    });

    // assert global links
    assertThat(found.getLinks()).anyMatch((link) -> link.getRel().equals(Linkable.SELF));

    // verify that featureService.getServices() was called
    verify(DAO).getServices();
  }
}
