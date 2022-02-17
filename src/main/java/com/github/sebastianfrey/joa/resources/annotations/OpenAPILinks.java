package com.github.sebastianfrey.joa.resources.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.models.Service;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.Binding;

@ProvideLink(value = Service.class, rel = Linkable.SERVICE_DESC,
    type = MediaType.APPLICATION_OPENAPI_JSON,
    bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
    style = InjectLink.Style.ABSOLUTE, title = "OpenAPI document as JSON")
@ProvideLink(value = Service.class, rel = Linkable.SERVICE_DESC,
    type = MediaType.APPLICATION_OPENAPI_YAML,
    bindings = @Binding(name = "serviceId", value = "${instance.serviceId}"),
    style = InjectLink.Style.ABSOLUTE, title = "OpenAPI document as YAML")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenAPILinks {
}
