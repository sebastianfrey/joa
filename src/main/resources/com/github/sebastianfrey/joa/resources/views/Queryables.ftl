<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.QueryablesView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout>
  <@components.nav>
    <@components.navlist>
      <@components.navitem href = "/">
        JOA
      </@components.navitem>
      <@components.navitem href = "">
        Services
      </@components.navitem>
      <@components.navitem href = queryables.toAbsoluteUrl("/${queryables.getServiceId()}")>
        ${queryables.getServiceId()}
      </@components.navitem>
      <@components.navitem href = queryables.toAbsoluteUrl("/${queryables.getServiceId()}/collections")>
        Collections
      </@components.navitem>
      <@components.navitem href = queryables.toAbsoluteUrl("/${queryables.getServiceId()}/collections/${queryables.getCollectionId()}")>
        ${queryables.getCollectionId()}
      </@components.navitem>
      <@components.navitem href = queryables.toAbsoluteUrl("/${queryables.getServiceId()}/collections/${queryables.getCollectionId()}/queryables") content = "">
        Queryables
      </@components.navitem>
    </@components.navlist>
    <div class="flex-grow"></div>
    <@components.navalternates linkable=queryables />
  </@components.nav>
  <@components.main>
    <@components.grid>
      <@components.griditem class = "flex-col">
        <@components.h1>Queryables</@components.h1>
        <@components.p>Queryables in this collection</@components.p>
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