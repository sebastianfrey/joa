<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ServiceView" -->
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
      <@components.navitem href="/api/${service.getServiceId()}" content="">
        ${service.getServiceId()}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=service />
  </@components.nav>

  <@components.main class="h-full">
    <@components.grid>
      <@components.griditem class="flex-col">
        <@components.h1>
          ${service.getServiceId()}
        </@components.h1>
        <@components.links links = [service.getFirstLinkByRel("data")]>
          <@components.h2 class="pt-4">
            Browse the available collections
          </@components.h2>
        </@components.links>
        <@components.links links = service.getLinksByRel("service-desc")>
          <@components.h2 class="pt-4">
            Open API 3.0
          </@components.h2>
        </@components.links>
        <@components.links links = [service.getFirstLinkByRelAndType("conformance", "text/html")]>
          <@components.h2 class="pt-4">
            Conformance
          </@components.h2>
        </@components.links>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>