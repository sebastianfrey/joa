import { createBounds, createLayers, createMap } from "./map.mjs";

const bbox = joa.extent.spatial.bbox[0];

const { lineLayer, fillLayer } = createLayers();
const { bounds, minx, miny, maxx, maxy } = createBounds(bbox);
const map = createMap({ bounds });;

map.on('load', () => {
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
    ...fillLayer,
    source: 'bbox',
  });
  // Add a golden outline around the polygon
  map.addLayer({
    ...lineLayer,
    source: 'bbox',
  });
});
