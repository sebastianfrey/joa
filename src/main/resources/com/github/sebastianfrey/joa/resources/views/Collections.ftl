<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.CollectionsView" -->
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
      <@components.navitem href="/api/${collections.getServiceId()}">
        ${collections.getServiceId()}
      </@components.navitem>
      <@components.navitem href="/api/${collections.getServiceId()}/collections" content="">
        Collections
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=collections />
  </@components.nav>

  <@components.main class="h-full">
    <h2 class="text-lg lg:text-xl font-medium text-black pb-4">
      Available Collections
    </h2>
    <@components.grid>
      <@components.griditem class="flex-col">
        <div class="w-full grid grid-cols-1 lg:grid-cols-3">
          <span class="hidden text-sm md:text-base lg:block lg:font-bold lg:p-2 lg:border-b-2">Title</span>
          <span class="hidden text-sm md:text-base lg:block lg:font-bold lg:p-2 lg:border-b-2">Description</span>
          <span class="hidden text-sm md:text-base lg:block lg:font-bold lg:p-2 lg:border-b-2">Type</span>
          <#list collections.getCollections() as collection>
            <span class="p-2 flex flex-row">
              <span class="font-bold text-sm md:text-base flex-grow lg:hidden">Title</span>
              <#assign collectionLink = collection.getFirstLinkByRelAndType("self", "text/html") />
              <span class="text-sm">
                <@components.link href="${collectionLink.getUri().toString()}" title="${collection.getTitle()}">
                  ${collection.getTitle()}
                </@components.link>
              </span>
            </span>
            <span class="p-2 flex flex-row">
              <span class="font-bold text-sm md:text-base flex-grow lg:hidden">Description</span>
              <span class="text-sm">
                <#if collection.getDescription()?? && collection.getDescription()?has_content>
                  ${collection.getDescription()}
                <#else>
                  -
                </#if>
              </span>
            </span>
            <span class="p-2 flex flex-row">
              <span class="font-bold text-sm md:text-base flex-grow lg:hidden">Type</span>
              <span class="text-sm">${collection.getItemType()}</span>
            </span>
            <#if collection?has_next>
              <span class="border-b-2 my-6 lg:hidden"></span>
            </#if>
          </#list>
        </div>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>