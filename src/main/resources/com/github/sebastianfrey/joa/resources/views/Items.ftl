<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ItemsView" -->
<#import "Layout.ftl" as layout>
<#import "Components.ftl" as components>
<#import "Icons.ftl" as icons>

<@layout.layout>
  <@components.nav>
    <@components.navlist>
      <@components.navitem href = "/">
        JOA
      </@components.navitem>
      <@components.navitem href = "/api">
        Services
      </@components.navitem>
      <@components.navitem href = "/api/${items.getServiceId()}">
        ${items.getServiceId()}
      </@components.navitem>
      <@components.navitem href = "/api/${items.getServiceId()}/collections">
        Collections
      </@components.navitem>
      <@components.navitem href = "/api/${items.getServiceId()}/collections/${items.getCollectionId()}">
        ${items.getCollectionId()}
      </@components.navitem>
      <@components.navitem href = "/api/${items.getServiceId()}/collections/${items.getCollectionId()}/items" content = "">
        Items
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
        <@components.map script = "/js/items.js" data = items.toJSON() class = "mb-6" />
        <#list items.getFeatures()>
          <div class="w-full min-h-[400px] overflow-auto border-2">
            <#assign first = items.getFeatures()[0] />
            <div class="w-full grid grid-cols-1 lg:grid-cols-[repeat(${first.getProperties()?size},_minmax(200px,_1fr))]">
              <#list first.getProperties()?keys>
                <#items as property>
                  <span class="hidden text-sm md:text-base lg:block lg:font-bold lg:p-2 lg:border-b-2">${property}</span>
                </#items>
              </#list>
              <#items as feature>
                <#list feature.getProperties()>
                  <#items as property, value>
                    <span class="p-2 flex flex-row items-center">
                      <span class="font-bold text-sm md:text-base flex-grow lg:hidden">${property}</span>
                      <span class="text-sm">
                        <#if value??>
                          <#if value?is_string>
                            <#if property == items.getIdColumn()>
                              <@components.link href="/api/${items.getServiceId()}/collections/${items.getCollectionId()}/items/${value}">
                                  ${value}
                              </@components.link>
                            <#else>
                              ${value}
                            </#if>
                          <#elseif value?is_number>
                            <#if property == items.getIdColumn()>
                              <@components.link href="/api/${items.getServiceId()}/collections/${items.getCollectionId()}/items/${value}">
                                  ${value?string}
                              </@components.link>
                            <#else>
                              ${value?string}
                            </#if>
                          <#elseif value?is_number>
                            ${value?string}
                          <#elseif value?is_boolean>
                            ${value?string}
                          <#elseif value?is_date>
                            ${value?datetime?string}
                          </#if>
                        <#else>
                          -
                        </#if>
                      </span>
                    </span>
                  </#items>
                </#list>
                <#sep>
                  <span class="border-b-2 my-6 lg:hidden"></span>
                </#sep>
              </#items>
            </div>
          </div>
        <#else>
          No Features
        </#list>
      </@components.griditem>
    </@components.grid>
  </@components.main>
</@layout.layout>