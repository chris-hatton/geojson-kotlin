package geojson.gson

import com.google.gson.GsonBuilder

fun GsonBuilder.registerGeoJsonTypeAdapters() : GsonBuilder {
    this.registerTypeAdapterFactory( GeoJsonTypeAdapterFactory() )
    return this
}