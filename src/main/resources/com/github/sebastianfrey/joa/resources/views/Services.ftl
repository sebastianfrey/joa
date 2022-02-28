<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ServicesView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout title = "${messages.get(\"services\")}">
  <@components.nav>
    <@components.navlist>
      <@components.navitem href=context.toAbsoluteUrl("") content="">
        ${messages.get("services")}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=services />
  </@components.nav>

  <@components.main>
    <@components.grid>
      <@components.griditem class="flex-col">
        <#list services.getServices() as service>
          <#assign link = service.getFirstLinkByRel("self") />
          <div class="flex flex-row w-full my-3 space-x-4">
            <div class="shrink-0">
              <img class="h-40 w-40" src="/assets/img/placeholder-200x200.png" alt="Thumbnail">
            </div>
            <div class="flex flex-col flex-1 h-full">
              <h2 class="font-medium text-black">
                <@components.link href="${link.getUri().toString()}" title="${service.getTitle()}" class="text-base lg:text-lg">
                  ${service.getTitle()}
                </@components.link>
              </h2>
              <p class="text-sm lg:text-base text-slate-500">
                ${service.getDescription()!"${messages.get(\"missing.description\")}"}
              </p>
            </div>
          </div>
        </#list>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>