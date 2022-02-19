<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ConformanceView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout>
  <@components.nav>
    <@components.navlist>
      <@components.navitem href = "/">
        JOA
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("")>
        Services
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${conformance.getServiceId()}")>
        ${conformance.getServiceId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${conformance.getServiceId()}/conformance") content="">
        Collections
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=conformance />
  </@components.nav>
  <@components.main>
    <@components.grid>
      <@components.griditem class = "flex-col">
        <@components.h1>Conformance</@components.h1>
        <@components.p>This API conforms to the following specification classes</@components.p>
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