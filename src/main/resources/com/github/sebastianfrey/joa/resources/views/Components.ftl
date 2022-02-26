<#import "Icons.ftl" as icons>

<#macro h1 class = "">
  <h1 class="text-lg font-medium pb-4 ${class}">
    <#nested />
  </h1>
</#macro>

<#macro h2 class = "">
  <h2 class="test-base fond-bold pb-2 ${class}">
    <#nested />
  </h2>
</#macro>

<#macro p class = "">
  <p class="text-base pb-4">
    <#nested />
  </p>
</#macro>

<#macro list>
  <ul class="list-disc pl-6">
    <#nested />
  </ul>
</#macro>

<#macro button onclick="" disabled=false>
  <#if disabled>
    <button disabled class="bg-[#ffe082] text-sm font-bold py-2 px-4 rounded opacity-50 cursor-not-allowed">
      <#nested />
    </button>
  <#else>
    <button type="button" onclick="${onclick}" class="bg-[#ffe082] text-sm hover:bg-[#caae53] font-bold py-2 px-4 rounded">
      <#nested />
    </button>
  </#if>
</#macro>

<!-- Main -->
<#macro main class = "">
  <div class="m-auto w-full p-6 pb-12 mb-6 lg:w-3/4 ${class}">
    <#nested />
  </div>
</#macro>

<#macro nav>
  <div class="bg-[#ffe082] w-full">
    <nav class="m-auto w-full px-6 py-1 lg:w-3/4 flex flex-row">
      <#nested />
    </nav>
  </div>
</#macro>

<#macro navlist class = "">
  <div class="flex flex-row flex-wrap items-center h-auto ${class}">
    <#nested />
  </div>
</#macro>

<#macro navitem href = "" title = "" content = "/">
  <#if href != "">
    <a class="text-base hover:underline" href="${href}" title="${title}">
      <#nested />
    </a>
  <#else>
      <#nested />
  </#if>
  <#if content?has_content>
    <span class="m-1 select-none">${content}</span>
  </#if>
</#macro>

<#macro navalternates linkable>
  <@components.navlist class = "ml-2 justify-end">
    <#list linkable.getLinksByRel("alternate") as link>
      <@components.navitem href = "${link.getUri().toString()}" title = "${link.getTitle()}" content="">
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

<#macro griditem class = "">
  <div class="p-6 bg-white shadow-xl flex items-start w-full h-full ${class}">
    <#nested />
  </div>
</#macro>

<#macro link href title = "" class = "">
  <a class="flex flex-row text-sm text-[#caae53] hover:underline ${class}" href="${href}" title="${title}">
    <#nested>
    <@icons.externallink class = "stroke-[#caae53] pl-1" />
  </a>
</#macro>

<#macro links links>
  <#nested />
  <#list links>
    <@components.list>
      <#items as link>
        <li>
          <#assign uri = link.getUri().toString() />
          <#assign title = uri />
          <#if link.getTitle()??>
            <#assign title = link.getTitle() />
          </#if>
          <@components.link href = "${uri}" title = "${title}">
            ${title}
          </@components.link>
        </li>
      </#items>
    </@components.list>
  <#else>
    -
  </#list>
</#macro>

<#macro properties feature context box=false idColumn="" serviceId="" collectionId="">
  <#list feature.getProperties()>
    <#items as property, rawvalue>
      <#assign value = rawvalue!"<null>">
      <#assign labelClasses = "">
      <#assign valueClasses = "">
      <#if box == false>
         <#assign labelClasses = "lg:hidden">
      </#if>
      <span class="p-2 flex flex-row items-center">
        <#if value?is_string>
          <#if !(value?has_content)>
            <#assign value = "-" />
          </#if>
        <#elseif value?is_number>
          <#assign value = value?string />
          <#assign valueClasses = "lg:flex-grow lg:text-right">
        <#elseif value?is_boolean>
          <#assign value = value?string />
        <#elseif value?is_date_only>
          <#assign value = value?date?iso_utc />
        <#elseif value?is_datetime>
          <#assign value = value?datetime?iso_utc />
        <#elseif value?is_time>
          <#assign value = value?time?iso_utc />
        <#elseif value?is_unknown_date_like>
          <#assign value = value?datetime?iso_utc />
        <#else>
          <#assign value = "<unsupported>" />
        </#if>

        <span class="font-bold text-smhref md:text-base flex-grow ${labelClasses}">${property}</span>
        <span class="text-sm overflow-hidden whitespace-nowrap text-ellipsis ${valueClasses}">
          <#if idColumn?has_content && property == idColumn>
            <@components.link href = context.toAbsoluteUrl("/${serviceId}/collections/${collectionId}/items/${value}")>
                ${value}
            </@components.link>
          <#else>
            <#if value?matches("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")>
              <@components.link href="${value}" title="${value}">
                ${messages.get("open.link")}
              </@components.link>
            <#else>
              ${value}
            </#if>
          </#if>
        </span>
      </span>
    </#items>
  </#list>
</#macro>

<#macro map script data class = "">
  <div id="map" class="${class}"></div>
   <script src="https://unpkg.com/maplibre-gl@1.15.2/dist/maplibre-gl.js"></script>
   <link href="https://unpkg.com/maplibre-gl@1.15.2/dist/maplibre-gl.css" rel="stylesheet"/>
  <script src="https://cdn.jsdelivr.net/npm/@turf/turf@6/turf.min.js"></script>
  <script>
    joa = JSON.parse(<#outputformat "JSON">'${data}'</#outputformat>);
  </script>
  <script type="module" src="${script}"></script>
</#macro>

<#macro spatial spatial>
  <#nested />
  <#list spatial.getBbox()>
    <ul class="text-sm">
      <#items as bbox>
        <li class="py-1 flex flex-col">
          <span class="flex space-x-4">
            <span class="min-w-[85px]">${messages.get("spatial.bbox.lowerleft")}</span>
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
            <span class="min-w-[85px]">${messages.get("spatial.bbox.upperright")}</span>
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
  <@components.map script = "/js/overview.mjs" data = collection.toJSON() class="h-[400px] w-full mt-2 border-2 mb-6"/>
</#macro>


<#macro temporal temporal>
  <#nested />
  <#list temporal.getInterval()>
    <ul class="text-sm">
      <#items as interval>
        <li class="py-1 flex">
          <span class="flex min-w-[120px] space-x-4">
            <span>${messages.get("temporal.interval.lower")}</span>
            <span>${interval[0]!"-"}</span>
          </span>
          <span class="flex min-w-[120px] space-x-4">
            <span class="pl-1">${messages.get("temporal.interval.upper")}</span>
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
    <ul class="list-disc pl-6">
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

<#macro pagination links>
  <div class="my-2">
    <#assign firstHref = "" />
    <#assign prevHref = "" />
    <#assign nextHref = "" />
    <#assign lastHref = "" />

    <#list links as link>
      <#if link.getType() == "text/html">
        <#if link.getRel() == "first">
          <#assign firstHref = link.getUri().toString() />
        <#elseif link.getRel() == "prev">
          <#assign prevHref = link.getUri().toString() />
        <#elseif link.getRel() == "next">
          <#assign nextHref = link.getUri().toString() />
        <#elseif link.getRel() == "last">
          <#assign lastHref = link.getUri().toString() />
        </#if>
      </#if>
    </#list>

    <#if firstHref?has_content>
      <@components.button onclick = "location.href='${firstHref}'">
        ${messages.get("pagination.first")}
      </@components.button>
    <#else>
      <@components.button disabled = true>${messages.get("pagination.first")}</@components.button>
    </#if>
    <#if prevHref?has_content>
      <@components.button onclick = "location.href='${prevHref}'">
        ${messages.get("pagination.prev")}
      </@components.button>
    <#else>
      <@components.button disabled = true>${messages.get("pagination.prev")}</@components.button>
    </#if>
    <#if nextHref?has_content>
      <@components.button onclick = "location.href='${nextHref}'">
        ${messages.get("pagination.next")}
      </@components.button>
    <#else>
      <@components.button disabled = true>${messages.get("pagination.next")}</@components.button>
    </#if>
    <#if lastHref?has_content>
      <@components.button onclick = "location.href='${lastHref}'">
        ${messages.get("pagination.last")}
      </@components.button>
    <#else>
      <@components.button disabled = true>${messages.get("pagination.last")}</@components.button>
    </#if>
  </div>
</#macro>