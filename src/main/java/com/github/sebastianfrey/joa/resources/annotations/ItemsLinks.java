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
    style = InjectLink.Style.ABSOLUTE, title = "This document as HTML")
@ProvideLink(value = Items.class, rel = Linkable.SELF, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "This document as JSON")
@ProvideLink(value = Collection.class, rel = Linkable.ITEMS, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "Items as HTML")
@ProvideLink(value = Collection.class, rel = Linkable.ITEMS, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    style = InjectLink.Style.ABSOLUTE, title = "Items as JSON")
@ProvideLink(value = Items.class, rel = Linkable.NEXT, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.nextPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "Next page of features as HTML")
@ProvideLink(value = Items.class, rel = Linkable.NEXT, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.nextPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "Next page of features as JSON")
@ProvideLink(value = Items.class, rel = Linkable.PREV, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.prevPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "Previous page of features as HTML")
@ProvideLink(value = Items.class, rel = Linkable.PREV, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.prevPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "Previous page of features as JSON")
@ProvideLink(value = Items.class, rel = Linkable.FIRST, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.firstPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "Last page of features as HTML")
@ProvideLink(value = Items.class, rel = Linkable.FIRST, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.firstPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "First page of features as JSON")
@ProvideLink(value = Items.class, rel = Linkable.LAST, type = MediaType.TEXT_HTML,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.lastPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "Last page of features as HTML")
@ProvideLink(value = Items.class, rel = Linkable.LAST, type = MediaType.APPLICATION_GEO_JSON,
    bindings = {@Binding(name = "serviceId", value = "${instance.serviceId}"),
        @Binding(name = "collectionId", value = "${instance.collectionId}"),},
    condition = "${instance.lastPageAvailable}", style = InjectLink.Style.ABSOLUTE,
    title = "Last page of features as JSON")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ItemsLinks {
}
