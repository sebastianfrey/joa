<#import "Icons.ftl" as icons>

<!-- Main -->
<#macro main class="">
  <div class="m-auto w-full p-6 pb-12 mb-6 lg:w-3/4 lg:px-0 ${class}">
    <#nested />
  </div>
</#macro>

<#macro nav>
  <div class="bg-[#ffe082] px-6 py-4 lg:px-0 w-full">
    <nav class="m-auto w-full lg:w-3/4 flex flex-col sm:flex-row">
      <#nested />
    </nav>
  </div>
</#macro>


<#macro navlist class="">
  <ol class="h-2 flex flex-row flex-wrap items-center h-[25px] sm:flex-nowrap sm:h-0 ${class}">
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
  <@components.navlist class="justify-end">
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

<#macro spatial spatial>
  <#nested />
  <#list spatial.getBbox()>
    <ul class="text-sm">
      <#items as bbox>
        <li class="py-1 flex flex-col">
          <span class="flex space-x-4">
            <span class="min-w-[85px]">Lower Left:</span>
            <#assign i = 0>
            <span>${bbox[i]?c!"-"}</span>
            <#assign i = i + 1>
            <span>${bbox[i]?c!"-"}</span>
            <#if bbox?size == 6>
              <#assign i = i + 1>
              <span>${bbox[i]?c!"-"}</span>
            </#if>
          </span>
          <span class="flex space-x-4">
            <span class="min-w-[85px]">Upper Right:</span>
            <#assign i = i + 1>
            <span>${bbox[i]?c!"-"}</span>
            <#assign i = i + 1>
            <span>${bbox[i]?c!"-"}</span>
            <#if bbox?size == 6>
              <#assign i = i + 1>
              <span>${bbox[i]?c!"-"}</span>
            </#if>
          </span>
        </li>
      </#items>
    </ul>
    <#else>
      -
  </#list>
  <div id="map" class="h-[400px] w-full mt-2"></div>
  <script src='https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.js'></script>
  <link href='https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.css' rel='stylesheet' />
  <script>
    mapboxgl.accessToken = 'pk.eyJ1Ijoic2ViYXN0aWFuZnJleSIsImEiOiJja3puYWI1bGYwN21sMnhxc2xnaGk4bDl1In0.EePnZo2Vsk02AN721nHZwQ';
    joa = JSON.parse(<#outputformat "JSON">'${collection.toJSON()}'</#outputformat>);
  </script>
  <script src="/js/overview.js"></script>
</#macro>

<#macro temporal temporal>
  <#nested />
  <#list temporal.getInterval()>
    <ul class="text-sm">
      <#items as interval>
        <li class="py-1 flex">
          <span class="flex min-w-[120px] space-x-4">
            <span>Lower:</span>
            <span>${interval[0]!"-"}</span>
          </span>
          <span class="flex min-w-[120px] space-x-4">
            <span class="pl-1">Upper:</span>
            <span>${interval[1]!"-"}</span>
          </span>
        </li>
      </#items>
    </ul>
  <#else>
    -
  </#list>
</#macro>

<#macro crs crs>
  <#nested />
  <#list crs>
    <ul class="text-sm">
      <#items as value>
        <li>
          <@link href=value title=value>
            ${value}
          </@link>
        </li>
      </#items>
    </ul>
  </#list>
</#macro>