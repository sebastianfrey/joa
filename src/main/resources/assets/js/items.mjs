import { createBounds, createLayers, createMap } from "./map.mjs";

const data = joa;
const bbox = data.bbox;
const geometryType = data.geometryType;

const { circleLayer, lineLayer, fillLayer } = createLayers();
const { bounds } = createBounds(bbox);
const map = createMap({ bounds });

map.on('load', () => {
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