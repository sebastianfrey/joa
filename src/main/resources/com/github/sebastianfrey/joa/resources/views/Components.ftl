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

<#macro map data>
  <script src='https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.js'></script>
  <link href='https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.css' rel='stylesheet' />
  <div id="map" class="h-[400px]"></div>
  <script>
    mapboxgl.accessToken = 'pk.eyJ1Ijoic2ViYXN0aWFuZnJleSIsImEiOiJja3puYWI1bGYwN21sMnhxc2xnaGk4bDl1In0.EePnZo2Vsk02AN721nHZwQ';

    <#outputformat "JSON">
    const data = JSON.parse('${data.toJSON()}');
    </#outputformat>

    const bbox = data.extent.spatial.bbox[0];
    let minx, miny, minz, maxx, maxy, maxz;
    if (bbox.length === 4) {
      [minx, miny, maxx, maxy] = bbox;
    } else {
      [minx, miny, minz,maxx, maxy, maxz] = bbox;
    }

    const bounds = [[minx, miny], [maxx, maxy]];
    const map = new mapboxgl.Map({
      container: 'map',
      style: 'mapbox://styles/mapbox/streets-v11',
      bounds,
    });
    map.on('load', () => {
      map.fitBounds(bounds, { padding: 50 });
      // add bbox source
      map.addSource('bbox', {
        type: 'geojson',
        data: {
          type: 'Feature',
          geometry: {
            type: 'Polygon',
            coordinates: [
              [
                [minx, miny],
                [minx, maxy],
                [maxx, maxy],
                [maxx, miny],
                [minx, miny],
              ]
            ]
          }
        }
      });
      // Add a new layer to visualize the polygon.
      map.addLayer({
        id: 'fill',
        type: 'fill',
        source: 'bbox', // reference the data source
        layout: {},
        paint: {
          'fill-color': '#0080ff', // blue color fill
          'fill-opacity': 0.5
        }
      });
      // Add a black outline around the polygon.
      map.addLayer({
        id: 'outline',
        type: 'line',
        source: 'bbox',
        layout: {},
        paint: {
          'line-color': '#000',
          'line-width': 3
        }
      });
    });
  </script>
</#macro>