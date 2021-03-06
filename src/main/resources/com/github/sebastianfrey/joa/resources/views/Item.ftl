<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ItemView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout title = "${item.getServiceId()} - ${item.getCollectionId()} - ${item.getId()}">
  <@components.nav>
    <@components.navlist>
      <@components.navitem href = context.toAbsoluteUrl("")>
        ${messages.get("services")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${item.getServiceId()}")>
        ${item.getServiceId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${item.getServiceId()}/collections")>
        ${messages.get("collections")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${item.getServiceId()}/collections/${item.getCollectionId()}")>
        ${item.getCollectionId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${item.getServiceId()}/collections/${item.getCollectionId()}/items")>
        ${messages.get("items")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${item.getServiceId()}/collections/${item.getCollectionId()}/items/${item.getId()}") content = "">
        ${item.getId()}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=item />
  </@components.nav>
  <@components.main>
    <@components.grid>
      <@components.griditem class = "flex-col">
        <@components.h1>
          ${messages.get("collection.and.id", "${item.getCollectionId()}", "${item.getId()}")}
        </@components.h1>
        <@components.h2>
          ${messages.get("preview")}
        </@components.h2>
        <@components.map script = "/assets/js/item.mjs" data = item.toJSON() class = "h-[400px] w-full mt-2 border-2 mb-6" />
        <@components.h2>
          ${messages.get("attributes")}
        </@components.h2>
        <div class="w-full grid grid-cols-1 border-2">
          <@components.properties feature = item context = item box = true/>
        </div>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>