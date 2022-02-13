<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ServiceView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout>
  <@components.header>
    <@components.navlist>
      <@components.navitem href="/">
        JOA
      </@components.navitem>
      <@components.navitem href="/api">
        Services
      </@components.navitem>
      <@components.navitem>
        ${service.getTitle()}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=service />
  </@components.header>

  <@components.main class="h-full">
    <@components.grid>
      <@components.griditem class="flex-col">
        <h2 class="text-lg lg:text-xl font-medium text-black pb-4">
          Collections
        </h2>
        <div class="text-sm lg:text-base text-[#caae53] justify-items-center">
          <#assign collectionsLink = service.getFirstLinkByRel("data") />
          <a class="hover:underline flex flex-row" href="${collectionsLink.getUri().toString()}" title="Browse the available collections">Browse the available collections <@icons.externallink class="pl-1" /></a>
        </div>
        <h2 class="text-lg lg:text-xl font-medium text-black pb-4 pt-4">
          Open API 3.0
        </h2>
        <#list service.getLinksByRel("service-desc") as openApiLink>
          <div class="text-sm lg:text-base text-[#caae53] justify-items-center">
            <a class="hover:underline flex flex-row" href="${openApiLink.getUri().toString()}" title="View the OpenAPI document (${openApiLink.getTitle()})">View the OpenAPI document (${openApiLink.getTitle()}) <@icons.externallink class="pl-1" /></a>
          </div>
        </#list>
        <h2 class="text-lg lg:text-xl font-medium text-black pb-4 pt-4">
          Conformance
        </h2>
        <div class="text-sm lg:text-base text-[#caae53] justify-items-center">
          <#assign conformanceLink = service.getFirstLinkByRel("conformance") />
          <a class="hover:underline flex flex-row" href="${conformanceLink.getUri().toString()}" title="View the supported conformance classes">View the supported conformance classes <@icons.externallink class="pl-1"/></a>
        </div>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>