<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.CollectionView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout title = "${collection.getServiceId()} - ${collection.getCollectionId()}">
  <@components.nav>
    <@components.navlist>
      <@components.navitem href = context.toAbsoluteUrl("")>
        ${messages.get("services")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${collection.getServiceId()}")>
        ${collection.getServiceId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${collection.getServiceId()}/collections")>
        ${messages.get("collections")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${collection.getServiceId()}/collections/${collection.getCollectionId()}") content = "">
        ${collection.getCollectionId()}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable = collection />
  </@components.nav>

  <@components.main class="h-full">
    <@components.grid>
      <@components.griditem class="flex-col">
        <@components.h1>
          ${collection.getTitle()}
        </@components.h1>
        <p class="pb-2">
          <#if collection.getDescription()?has_content>
            ${collection.getDescription()}
          <#else>
            ${messages.get("missing.description")}
          </#if>
        </p>
        <div class="w-full flex justify-end">
          <#assign itemsLink = collection.getFirstLinkByRelAndType("items", "text/html") />
          <@components.link href = "${itemsLink.getUri().toString()}" title = "${itemsLink.getTitle()}">
            ${messages.get("view.data")}
          </@components.link>
        </div>
        <div class="pb-4 w-full">
          <@components.spatial spatial = collection.getExtent().getSpatial()>
            <@components.h2>${messages.get("spatial.extent")}</@components.h2>
          </@components.spatial>
        </div>
        <div class="pb-4 w-full">
          <@components.temporal temporal = collection.getExtent().getTemporal()>
            <@components.h2>${messages.get("temporal.extent")}</@components.h2>
          </@components.temporal>
        </div>
        <div class="pb-4 w-full">
          <@components.crs crs = collection.getCrs()>
            <@components.h2>${messages.get("supported.crs")}</@components.h2>
          </@components.crs>
        </div>
        <div class="pb-4 w-full">
          <@components.links links = [collection.getFirstLinkByRel("http://www.opengis.net/def/rel/ogc/1.0/queryables")]>
            <@components.h2>${messages.get("schemas")}</@components.h2>
          </@components.links>
        </div>
        <@components.links links = collection.getLinks()>
          <@components.h2>${messages.get("links")}</@components.h2>
        </@components.links>
      </@components.griditem>
    </@components.grid>

  </@components.main>
</@layout.layout>
