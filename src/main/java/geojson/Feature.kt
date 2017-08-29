package geojson

import com.google.gson.JsonObject
import geojson.geometry.Geometry

data class Feature(
        val id         : String? = null,
        val geometry   : Geometry<*>,
        val properties : JsonObject = JsonObject()
    ) : GeoJsonObject()