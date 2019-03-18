package geojson.gson

import geojson.GeoJsonObject
import kotlin.reflect.KClass

/**
 * Codifies a mapping between GeoJSON types and their Kotlin-class representations.
 * Used by [GeoJsonSerializer] and [GeoJsonDeserializer].
 */
sealed class GeoJsonType(val typeValue: String, val `class`: KClass<*>) {

    sealed class Geometry(typeValue: String, `class`: KClass<*>) : GeoJsonType(typeValue, `class`) {

        companion object {
            const val coordinatesKey = "coordinates"
        }

        object Point : Geometry(typeValue = "Point", `class` = geojson.geometry.impl.Point::class)
        object MultiPoint : Geometry(typeValue = "MultiPoint", `class` = geojson.geometry.impl.MultiPoint::class)
        object LineString : Geometry(typeValue = "LineString", `class` = geojson.geometry.impl.LineString::class)
        object MultiLineString : Geometry(typeValue = "MultiLineString", `class` = geojson.geometry.impl.MultiLineString::class)
        object Polygon : Geometry(typeValue = "Polygon", `class` = geojson.geometry.impl.Polygon::class)
        object MultiPolygon : Geometry(typeValue = "MultiPolygon", `class` = geojson.geometry.impl.MultiPolygon::class)
    }

    object Feature : GeoJsonType(typeValue = "Feature", `class` = geojson.Feature::class) {
        const val idKey = "id"
        const val propertiesKey = "properties"
        const val geometryKey = "geometry"
    }

    object FeatureCollection : GeoJsonType(typeValue = "FeatureCollection", `class` = geojson.FeatureCollection::class) {
        const val featuresKey = "features"
        const val totalFeaturesKey = "totalFeatures"
    }

    companion object {

        const val typeKey = "type"

        private val allTypes: MutableList<GeoJsonType> = mutableListOf(
                GeoJsonType.Geometry.Point,
                GeoJsonType.Geometry.MultiPoint,
                GeoJsonType.Geometry.LineString,
                GeoJsonType.Geometry.MultiLineString,
                GeoJsonType.Geometry.Polygon,
                GeoJsonType.Geometry.MultiPolygon,
                GeoJsonType.Feature,
                GeoJsonType.FeatureCollection
        )

        fun forString(typeName: String): GeoJsonType? = allTypes.find { it.typeValue == typeName }
        fun forObject(obj: GeoJsonObject): GeoJsonType? = allTypes.find { it.`class` == obj::class }
    }
}



