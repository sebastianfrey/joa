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
    <@components.map data=(collection) />
  </@components.main>
</@layout.layout>