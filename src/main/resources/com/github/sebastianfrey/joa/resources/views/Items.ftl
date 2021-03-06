<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ItemsView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout title = "${item.getServiceId()} - ${item.getCollectionId()} - ${messages.get(\"items\")}">
  <@components.nav>
    <@components.navlist>
      <@components.navitem href = context.toAbsoluteUrl("")>
        ${messages.get("services")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${items.getServiceId()}")>
        ${items.getServiceId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${items.getServiceId()}/collections")>
        ${messages.get("collections")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${items.getServiceId()}/collections/${items.getCollectionId()}")>
        ${items.getCollectionId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${items.getServiceId()}/collections/${items.getCollectionId()}/items") content = "">
        ${messages.get("items")}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=items />
  </@components.nav>
  <@components.main>
    <@components.grid>
      <@components.griditem class = "flex-col">
        <@components.h1>
          ${items.getCollectionId()}
        </@components.h1>
        <@components.p>
          ${messages.get("items.of.collection", "${items.getCollectionId()}")}
        </@components.p>
        <@components.pagination links = items.getLinks() />
        <@components.map script = "/assets/js/items.mjs" data = items.toJSON() class = "h-[400px] w-full mt-2 border-2 mb-6" />
        <#list items.getFeatures()>
          <div class="w-full lg:max-h-[400px] overflow-auto border-2 relative">
            <#assign first = items.getFeatures()[0] />
            <div class="w-full grid grid-cols-1 lg:grid-cols-[repeat(${first.getProperties()?size},_minmax(300px,_1fr))] sticky z-index-1 top-0 bg-white">
              <#list first.getProperties()?keys>
                <#items as property>
                  <span class="hidden bg-white text-sm overflow-hidden whitespace-nowrap text-ellipsis md:text-base lg:block lg:font-bold lg:p-2 lg:border-b-2">${property}</span>
                </#items>
              </#list>
            </div>
            <div class="w-full grid grid-cols-1 lg:grid-cols-[repeat(${first.getProperties()?size},_minmax(300px,_1fr))]">
              <#items as feature>
                <@components.properties feature = feature context = context idColumn = items.getIdColumn() serviceId = items.getServiceId() collectionId = items.getCollectionId() />
                <#sep>
                  <span class="border-b-2 my-6 lg:hidden"></span>
                </#sep>
              </#items>
            </div>
          </div>
        <#else>
           ${messages.get("no.items.in.collection", "${items.getCollectionId()}")}
        </#list>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>