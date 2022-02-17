const data = joa;
const bbox = data.bbox;
const geometryType = data.geometryType;

const lineLayer = {
  id: 'outline',
  type: 'line',
  layout: {},
  paint: {
    'line-color': '#caae53',
    'line-width': 2
  },
};
const fillLayer = {
  id: 'fill',
  type: 'fill',
  layout: {},
  paint: {
    'fill-color': '#ffe082', // amber color fill
    'fill-opacity': 0.5
  },
};
const circleLayer = {
  id: 'circle',
  type: 'circle',
  layout: {},
  paint: {
    'circle-color': '#ffe082',
    'circle-stroke-color': '#caae53',
    'circle-stroke-width': 2,
  },
};

let minx, miny, minz, maxx, maxy, maxz;

if (bbox.length === 4) {
  [minx, miny, maxx, maxy] = bbox;
} else {
  [minx, miny, minz, maxx, maxy, maxz] = bbox;
}
const bounds = [
  [minx, miny],
  [maxx, maxy]
];

const map = new mapboxgl.Map({
  container: 'map',
  style: 'mapbox://styles/mapbox/light-v10',
  bounds,
});

map.on('load', () => {
  map.fitBounds(bounds, {
    padding: 50,
    duration: 0
  });

  const sourceInfos = [];

  const createSourceInfo = (data, name, layers) => ({
    name,
    source: {
      type: 'geojson',
      data
    },
    layers: layers.map((layer) => ({ ...layer, id: `${name}-${layer.id}` })),
  });

  const createPointsSourceInfo = (data) => createSourceInfo(data, 'points', [circleLayer]);
  const createLinesSourceInfo = (data) => createSourceInfo(data, 'lines', [lineLayer]);
  const createPolygonsSourceInfo = (data) => createSourceInfo(data, 'polygons', [lineLayer, fillLayer]);

  switch (geometryType) {
    case "Point":
    case "MultiPoint":
      sourceInfos.push(createPointsSourceInfo(data));
      break;

    case "LineString":
    case "MultiLineString":
      sourceInfos.push(createLinesSourceInfo(data));
      break;

    case "Polygon":
    case "MultiPolygon":
      sourceInfos.push(createPolygonsSourceInfo(data));
      break;

    default:
      const pointFeatures = [],
        lineFeatures = [],
        polygonFeatures = [];

      for (const feature of data.features) {
        switch (feature.geometry.type) {
          case "Point":
          case "MultiPoint":
            pointFeatures.push(feature);
            break;

          case "LineString":
          case "MultiLineString":
            lineFeatures.push(feature);
            break;

          case "Polygon":
          case "MultiPolygon":
            polygonFeatures.push(feature);
            break;
        }
      }

      sourceInfos.push(
        createPointsSourceInfo({
          ...data,
          features: pointFeatures
        }),
        createLinesSourceInfo({
          ...data,
          features: lineFeatures
        }),
        createPolygonsSourceInfo({
          ...data,
          features: polygonFeatures
        }),
      );

      break;
  }


  for (const {
      name,
      source,
      layers
    } of sourceInfos) {
    map.addSource(name, source);
    for (const layer of layers) {
      map.addLayer({
        ...layer,
        source: name,
      });
    }
  }
});