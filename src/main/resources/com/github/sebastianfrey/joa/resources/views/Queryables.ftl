<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.QueryablesView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout title = "${queryables.getServiceId()} - ${queryables.getCollectionId()} - ${messages.get(\"queryables\")}">
  <@components.nav>
    <@components.navlist>
      <@components.navitem href = "">
        ${messages.get("services")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${queryables.getServiceId()}")>
        ${queryables.getServiceId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${queryables.getServiceId()}/collections")>
        ${messages.get("collections")}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${queryables.getServiceId()}/collections/${queryables.getCollectionId()}")>
        ${queryables.getCollectionId()}
      </@components.navitem>
      <@components.navitem href = context.toAbsoluteUrl("/${queryables.getServiceId()}/collections/${queryables.getCollectionId()}/queryables") content = "">
        ${messages.get("queryables")}
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=queryables />
  </@components.nav>
  <@components.main>
    <@components.grid>
      <@components.griditem class = "flex-col">
        <@components.h1>${messages.get("queryables")}</@components.h1>
        <@components.p>${messages.get("queryables.in.collection")}</@components.p>
        <@components.list>
          <#list queryables.toJSON()?eval_json as key, values>
            <#if key == "properties" && values?is_hash>
              <#list values as property, schema>
                <li id="${property}">
                  ${property}
                  <i>
                  (
                    <#list schema as attribute, value>
                      <#assign attributeValue = "" />
                      <#if value?is_string>
                        <#assign attributeValue = value />
                      </#if>
                      <#if attributeValue?has_content>
                        ${attribute}=${attributeValue}
                      </#if>
                    </#list>
                  )
                  </i>
                </li>
              </#list>
            </#if>
          </#list>
        </@components.list>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>