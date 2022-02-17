package com.github.sebastianfrey.joa.resources.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.sebastianfrey.joa.models.Conformance;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.models.Service;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.Binding;

@ProvideLink(value = Conformance.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
    style = InjectLink.Style.ABSOLUTE, title = "This document as HTML")
@ProvideLink(value = Conformance.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
    style = InjectLink.Style.ABSOLUTE, title = "This document as JSON")
@ProvideLink(value = Service.class, rel = Linkable.CONFORMANCE, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
    style = InjectLink.Style.ABSOLUTE, title = "Conformance classes as HTML")
@ProvideLink(value = Service.class, rel = Linkable.CONFORMANCE, type = MediaType.APPLICATION_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}")},
    style = InjectLink.Style.ABSOLUTE, title = "Conformance classes as JSON")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConformanceLinks {
}
