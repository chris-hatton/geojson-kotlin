package geojson

import geojson.geometry.Geometry

/**
 *
 */
class GeometryCollection(val geometries: List<Geometry<*>>) : GeoJsonObject() {

}