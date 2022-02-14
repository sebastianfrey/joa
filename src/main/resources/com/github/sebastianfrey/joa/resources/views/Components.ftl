<#import "Icons.ftl" as icons>

<!-- Main -->
<#macro main class="">
  <div class="m-auto w-full p-6 pb-12 mb-6 lg:w-3/4 lg:px-0 ${class}">
    <#nested />
  </div>
</#macro>

<#macro nav>
  <div class="bg-[#ffe082] px-6 py-4 w-full">
    <nav class="m-auto w-full lg:w-3/4 flex flex-wrap ">
      <#nested />
    </nav>
  </div>
</#macro>


<#macro navlist>
  <ol class="h-2 flex flex-row items-center">
    <#nested />
  </ol>
</#macro>

<#macro navitem href="" title="" content=">">
  <li class="text-sm md:text-base">
    <#if href != "">
      <a class="hover:underline" href="${href}" title="${title}">
        <#nested />
      </a>
    <#else>
        <#nested />
    </#if>
    <#if content?has_content>
      <span class="m-1 select-none">${content}</span>
    </#if>
  </li>
</#macro>

<#macro navalternates linkable>
  <@components.navlist>
    <#list linkable.getLinksByRel("alternate") as link>
      <@components.navitem href="${link.getUri().toString()}" title="${link.getTitle()}" content="">
        <#if link.getType()?contains("json")>
          JSON
        <#else>
          ${link.getType()}
        </#if>
      </@components.navitem>
    </#list>
  </@components.navlist>
</#macro>

<#macro grid>
  <div class="grid grid-cols-1 gap-4">
    <#nested />
  </div>
</#macro>

<#macro griditem class="">
  <div class="p-6 bg-white shadow-xl flex items-start w-full h-full ${class}">
    <#nested />
  </div>
</#macro>

<#macro link href title="">
  <a class="flex flex-row text-sm lg:text-base text-[#caae53] hover:underline" href="${href}" title="${title}">
    <#nested>
    <@icons.externallink class="stroke-[#caae53] pl-1" />
  </a>
</#macro>