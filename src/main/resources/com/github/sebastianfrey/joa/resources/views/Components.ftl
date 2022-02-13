<!-- Main -->
<#macro main class="">
  <div class="p-6 m-auto w-full lg:w-3/4 ${class}">
    <#nested />
  </div>
</#macro>

<#macro header>
  <nav class="sticky top-0 bg-[#ffe082] p-6 flex flex-row">
    <#nested />
  </nav>
</#macro>

<#macro navlist>
  <ol class="h-2 flex flex-row items-center">
    <#nested />
  </ol>
</#macro>

<#macro navitem href="" title="" content=">">
  <li class="text-xl font-bold">
    <#if href != "">
      <a class="underline after:content-['${content}'] after:ml-0.5 after:mr-1 after:no-underline after:inline-block" href="${href}" title="${title}">
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
  <div class="p-6 bg-white shadow-lg flex items-start w-full h-full ${class}">
    <#nested />
  </div>
</#macro>