export function createMap(options = {}) {
  const map = new maplibregl.Map({
    container: 'map',
    style: {
      version: 8,
      maxZoom: 19,
      sources: {
        'raster-tiles': {
          type: 'raster',
          tiles: ['	https://tile.openstreetmap.org/{z}/{x}/{y}.png'],
          tileSize: 256,
          attribution: '© <a href="https://www.openstreetmap.org/copyright"> OpenStreetMap contributors</a>'
        }
      },
      layers: [{
        id: 'simple-tiles',
        type: 'raster',
        source: 'raster-tiles',
        minzoom: 0,
        maxzoom: 19,
      }]
    },
    ...options,
  });

  if (options.bounds) {
    map.fitBounds(options.bounds, {
      padding: 50,
      duration: 0,
    });
  }

  return map;
}

export function createBounds(bbox = []) {
  let minx, miny, minz, maxx, maxy, maxz;

  if (bbox.length === 4) {
    [minx, miny, maxx, maxy] = bbox;
  } else {
    [minx, miny, minz,maxx, maxy, maxz] = bbox;
  }
  const bounds = [[minx, miny], [maxx, maxy]];

  return {
    bounds,
    minx,
    miny,
    minz,
    maxx,
    maxy,
    maxz,
  };
}

export function createLayers() {
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

  return { lineLayer, fillLayer, circleLayer };
}