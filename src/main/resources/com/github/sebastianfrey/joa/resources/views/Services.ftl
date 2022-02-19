<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ServicesView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout>
  <@components.nav>
    <@components.navlist>
      <@components.navitem href="/">
        JOA
      </@components.navitem>
      <@components.navitem context.toAbsoluteUrl("") content="">
        Services
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=services />
  </@components.nav>

  <@components.main>
    <@components.grid>
      <#list services.getServices() as service>
        <@components.griditem class="space-x-4">
          <div class="shrink-0">
            <img class="h-40 w-40" src="/img/placeholder-200x200.png" alt="Thumbnail">
          </div>
          <div class="flex flex-col flex-1 h-full">
            <h2 class="text-sm lg:text-base font-medium text-black">${service.getTitle()}</h2>
            <p class="text-sm lg:text-base text-slate-500">${(service.getDescription())!"No description"}</p>
            <div class="flex-grow"></div>
            <#assign link = service.getFirstLinkByRel("self") />
            <div class="self-end">
              <@components.link href="${link.getUri().toString()}" title="Show Service">
                View
              </@components.link>
            </div>
          </div>
        </@components.griditem>
      </#list>
    </@components.grid>
  </@components.main>
</@layout.layout>