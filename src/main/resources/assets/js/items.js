const bbox = joa.bbox;
const geometryType = joa.geometryType;

const lineLayer = {
  id: 'outline',
  type: 'line',
  source: 'items',
  layout: {},
  paint: {
    'line-color': '#caae53',
    'line-width': 2
  },
};
const fillLayer = {
  id: 'fill',
  type: 'fill',
  source: 'items', // reference the data source
  layout: {},
  paint: {
    'fill-color': '#ffe082', // amber color fill
    'fill-opacity': 0.5
  },
};
const circleLayer = {
  id: 'circle',
  type: 'circle',
  source: 'items',
  layout: {},
  paint: {
    'circle-color': '#ffe082',
    'circle-stroke-color':  '#caae53',
    'circle-stroke-width':  2,
  },
};

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
  map.addSource('items', {
    type: 'geojson',
    data: joa
  });

  const layers = [];

  switch (geometryType) {
    case "Point":
    case "MultiPoint":
      layers.push(circleLayer);
      break;

    case "LineString":
    case "MultiLineString":
      layers.push(lineLayer);
      break;

    case "Polygon":
    case "MultiPolygon":
      layers.push(lineLayer, fillLayer);
      break;

    default:
      layers.push(circleLayer, lineLayer, fillLayer);
      break;
  }

  for (const layer of layers) {
    map.addLayer(layer);
  }
});
