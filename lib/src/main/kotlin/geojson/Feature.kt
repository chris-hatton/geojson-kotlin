package geojson

import geojson.geometry.Geometry

data class Feature(
        val id         : String? = null,
        val geometry   : Geometry<*>,
        val properties : Map<String,Any> = mapOf()
    ) : GeoJsonObject()