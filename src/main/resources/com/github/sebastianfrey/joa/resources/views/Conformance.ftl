<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ConformanceView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout title = "${conformance.getServiceId()} - ${messages.get(\"conformance\")}">
  <@components.nav>
    <@components.navlist>
      <@components.navitem href = context.toAbsoluteUrl("")>
        ${messages.get("services")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${conformance.getServiceId()}")>
        ${conformance.getServiceId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${conformance.getServiceId()}/conformance") content="">
        ${messages.get("conformance")}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=conformance />
  </@components.nav>
  <@components.main>
    <@components.grid>
      <@components.griditem class = "flex-col">
        <@components.h1>${messages.get("conformance")}</@components.h1>
        <@components.p>${messages.get("conforms.to.classes")}</@components.p>
        <@components.list>
          <#list conformance.getConformsTo() as conformsTo>
            <li>
              ${conformsTo}
            </li>
          </#list>
        </@components.list>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>