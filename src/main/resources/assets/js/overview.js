const bbox = joa.extent.spatial.bbox[0];

let minx, miny, minz, maxx, maxy, maxz;

if (bbox.length === 4) {
  [minx, miny, maxx, maxy] = bbox;
} else {
  [minx, miny, minz,maxx, maxy, maxz] = bbox;
}
const bounds = [[minx, miny], [maxx, maxy]];

const map = new mapboxgl.Map({
  container: 'map',
  style: 'mapbox://styles/mapbox/light-v10',
  bounds,
});

map.on('load', () => {
  map.fitBounds(bounds, { padding: 50, duration: 0 });
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
  // Add a new layer to visualize the polygon
  map.addLayer({
    id: 'fill',
    type: 'fill',
    source: 'bbox', // reference the data source
    layout: {},
    paint: {
      'fill-color': '#ffe082', // amber color fill
      'fill-opacity': 0.5
    }
  });
  // Add a golden outline around the polygon
  map.addLayer({
    id: 'outline',
    type: 'line',
    source: 'bbox',
    layout: {},
    paint: {
      'line-color': '#caae53',
      'line-width': 2
    }
  });
});
