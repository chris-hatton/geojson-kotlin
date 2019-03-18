package geojson.gson

import com.google.gson.*
import com.google.gson.internal.bind.TreeTypeAdapter
import com.google.gson.reflect.TypeToken
import geojson.GeoJsonObject

class GeoJsonTypeAdapterFactory(private val lenient: Boolean = false) : TypeAdapterFactory {

    companion object {
        val geoJsonObjectTypeToken: TypeToken<GeoJsonObject> = TypeToken.get(GeoJsonObject::class.java)
    }

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {

        val isGeoJsonObjectType = GeoJsonObject::class.java.isAssignableFrom(type.rawType)

        if (!isGeoJsonObjectType) {
            return null
        }

        val serializer: JsonSerializer<GeoJsonObject> = GeoJsonSerializer(lenient)
        val deserializer: JsonDeserializer<GeoJsonObject> = GeoJsonDeserializer(lenient)

        @Suppress("UNCHECKED_CAST")
        val adapter = TreeTypeAdapter<GeoJsonObject>(serializer, deserializer, gson, geoJsonObjectTypeToken, this) as TypeAdapter<T>

        return adapter
    }
}