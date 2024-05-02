const lat = parseFloat(document.getElementById('establecimiento-sugerencia-lat').value);
const lng = parseFloat(document.getElementById('establecimiento-sugerencia-lng').value);
const nombre = document.getElementById('establecimiento-sugerencia-nombre').value;

console.log(lat);
console.log(lng);
console.log(nombre);

// Set up the OSM layer
var myTileServer = new ol.layer.Tile({
  source: new ol.source.OSM({
    crossOrigin: null,
    url: 'https://{a-c}.tile.openstreetmap.org/{z}/{x}/{y}.png'
  })
});

// Create the map
var map = new ol.Map({
  layers: [ myTileServer ],
  target: 'map',
  view: new ol.View({
    center: ol.proj.transform([lng, lat], 'EPSG:4326', 'EPSG:3857'),
    zoom: 14
  })
});

var layer = new ol.layer.Vector({
  source: new ol.source.Vector({
      features: [
          new ol.Feature({
              geometry: new ol.geom.Point(ol.proj.fromLonLat([lng, lat]))
          })
      ]
  })
});

map.addLayer(layer);

var container = document.getElementById('popup');
var content = document.getElementById('popup-content');
var closer = document.getElementById('popup-closer');

var overlay = new ol.Overlay({
    element: container,
    autoPan: true,
    autoPanAnimation: {
        duration: 250
    }
});
map.addOverlay(overlay);

closer.onclick = function() {
    overlay.setPosition(undefined);
    closer.blur();
    return false;
};

map.on('singleclick', function (event) {
  if (map.hasFeatureAtPixel(event.pixel) === true) {
      var coordinate = event.coordinate;

      content.innerHTML = '<b>' + nombre + '</b>';
      overlay.setPosition(coordinate);
  } else {
      overlay.setPosition(undefined);
      closer.blur();
  }
});