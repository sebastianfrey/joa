package com.github.sebastianfrey.joa.resources.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.sebastianfrey.joa.models.Item;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.Binding;

@ProvideLink(value = Item.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),
        @Binding(name = "featureId", value = "${instance.id}"),},
    style = InjectLink.Style.ABSOLUTE, title = "this.html")
@ProvideLink(value = Item.class, rel = Linkable.SELF, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),
        @Binding(name = "featureId", value = "${instance.id}"),},
    style = InjectLink.Style.ABSOLUTE, title = "this.json")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ItemLinks {
}
