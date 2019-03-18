package geojson.gson

import com.google.gson.GsonBuilder

fun GsonBuilder.registerGeoJsonTypeAdapters(lenient: Boolean = false): GsonBuilder {
    this.registerTypeAdapterFactory(GeoJsonTypeAdapterFactory(lenient))
    return this
}