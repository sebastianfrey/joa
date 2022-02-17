package com.github.sebastianfrey.joa.resources.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Item;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.Binding;

@ProvideLink(value = Collection.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "Collection as HTML")
@ProvideLink(value = Collection.class, rel = Linkable.SELF, type = MediaType.APPLICATION_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "Collection as JSON")
@ProvideLink(value = Item.class, rel = Linkable.COLLECTION, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "Item as HTML")
@ProvideLink(value = Item.class, rel = Linkable.COLLECTION, type = MediaType.APPLICATION_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "Item as JSON")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CollectionLinks {
}
