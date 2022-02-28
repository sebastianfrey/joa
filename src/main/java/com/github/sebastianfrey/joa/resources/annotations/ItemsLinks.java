package com.github.sebastianfrey.joa.resources.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.sebastianfrey.joa.models.Collection;
import com.github.sebastianfrey.joa.models.Items;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.ProvideLink;
import org.glassfish.jersey.linking.Binding;

@ProvideLink(value = Items.class, rel = Linkable.SELF, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "this.html")
@ProvideLink(value = Items.class, rel = Linkable.SELF, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "this.json")
@ProvideLink(value = Collection.class, rel = Linkable.ITEMS, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "items.html")
@ProvideLink(value = Collection.class, rel = Linkable.ITEMS, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "items.json")
@ProvideLink(value = Items.class, rel = Linkable.NEXT, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.nextPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "items.next.html")
@ProvideLink(value = Items.class, rel = Linkable.NEXT, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.nextPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "items.next.json")
@ProvideLink(value = Items.class, rel = Linkable.PREV, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.prevPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "items.prev.html")
@ProvideLink(value = Items.class, rel = Linkable.PREV, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.prevPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "items.prev.json")
@ProvideLink(value = Items.class, rel = Linkable.FIRST, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.firstPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "items.first.html")
@ProvideLink(value = Items.class, rel = Linkable.FIRST, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.firstPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "items.first.json")
@ProvideLink(value = Items.class, rel = Linkable.LAST, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.lastPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "items.last.html")
@ProvideLink(value = Items.class, rel = Linkable.LAST, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.lastPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "items.last.json")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ItemsLinks {
}
