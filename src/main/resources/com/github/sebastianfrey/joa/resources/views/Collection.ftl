<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.CollectionView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout>
  <@components.nav>
    <@components.navlist>
      <@components.navitem href="/">
        JOA
      </@components.navitem>
      <@components.navitem href="/api">
        Services
      </@components.navitem>
      <@components.navitem href="/api/${collection.getServiceId()}">
        ${collection.getServiceId()}
      </@components.navitem>
      <@components.navitem href="/api/${collection.getServiceId()}/collections">
        Collections
      </@components.navitem>
      <@components.navitem href="/api/${collection.getServiceId()}/collections/${collection.getCollectionId()}" content="">
        ${collection.getCollectionId()}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=collection />
  </@components.nav>

  <@components.main class="h-full">
    <h2 class="text-lg lg:text-xl font-medium text-black pb-4">
      ${collection.getTitle()}
    </h2>

    <@components.grid>
      <@components.griditem class="flex-col">
        <@components.spatial spatial = collection.getExtent().getSpatial()>
          <h2 class="test-base lg:text-lg fond-bold pb-2">Spatial Extent</h2>
        </@components.spatial>
      </@components.griditem>
      <@components.griditem class="flex-col">
        <@components.temporal temporal = collection.getExtent().getTemporal()>
          <h2 class="test-base lg:text-lg fond-bold pb-2">Temporal Extent</h2>
        </@components.temporal>
      </@components.griditem>
      <@components.griditem class="flex-col">
        <@components.crs crs = collection.getCrs()>
          <h2 class="test-base lg:text-lg fond-bold pb-2">Supported CRS</h2>
        </@components.crs>
      </@components.griditem>
      <@components.griditem class="flex-col">
        <h2 class="test-base lg:text-lg fond-bold pb-2">Schemas</h2>
        <#assign link = collection.getFirstLinkByRel("http://www.opengis.net/def/rel/ogc/1.0/queryables") />
        <@components.link href="${link.getUri().toString()}" title="${link.getTitle()}">
          ${link.getTitle()}
        </@components.link>
      </@components.griditem>
    </@components.grid>

  </@components.main>
</@layout.layout>