<#import "Icons.ftl" as icons>

<!-- Main -->
<#macro main class="">
  <div class="m-auto w-full lg:w-3/4 mt-6 pb-12 mb-6 ${class}">
    <#nested />
  </div>
</#macro>

<#macro nav>
  <div class="bg-[#ffe082] p-4 w-full">
    <nav class="m-auto w-full lg:w-3/4 flex">
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
  <li class="text-xl font-bold">
    <#if href != "">
      <a class="hover:underline after:content-['${content}'] after:ml-0.5 after:mr-1 after:no-underline after:inline-block" href="${href}" title="${title}">
        <#nested />
      </a>
    <#else>
        <#nested />
    </#if>
  </li>
</#macro>

<#macro navalternates linkable>
  <@components.navlist>
    <#list linkable.getLinksByRel("alternate") as link>
      <@components.navitem href="${link.getUri().toString()}" title="This resource as ${link.getTitle()}" content="">
        ${link.getTitle()}
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
  <a class="flex flex-row text-sm lg:text-base text-[#caae53] hover:underline " href="${href}" title="${title}">
    <#nested>
    <@icons.externallink class="stroke-[#caae53] pl-1" />
  </a>
</#macro>