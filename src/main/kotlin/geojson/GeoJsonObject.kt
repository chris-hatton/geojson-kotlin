package geojson

import com.google.gson.GsonBuilder
import geojson.gson.registerGeoJsonTypeAdapters

/**
 * https://tools.ietf.org/html/rfc7946
 * Section 3
 */
abstract class GeoJsonObject {

    fun toJson() : String {
        return GsonBuilder().registerGeoJsonTypeAdapters().create().toJson(this)
    }

    companion object {
        inline fun <reified T:GeoJsonObject> fromJson( text: String ) : T {
            return GsonBuilder().registerGeoJsonTypeAdapters().create().fromJson(text, T::class.java)
        }
    }
}