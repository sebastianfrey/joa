package com.github.sebastianfrey.joa.resources.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.models.Services;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.InjectLink;

@ProvideLink(value = Services.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
    style = InjectLink.Style.ABSOLUTE, title = "this.html")
@ProvideLink(value = Services.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
    style = InjectLink.Style.ABSOLUTE, title = "this.json")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServicesLinks {
}
