package geojson.gson

import geojson.GeoJsonObject
import geojson.geometry.Geometry
import kotlin.reflect.KClass

/**
 * Codifies a mapping between GeoJSON types and their Kotlin-class representations.
 * Used by [GeometrySerializer] and [GeometryDeserializer].
 */
sealed class GeoJsonType( val typeValue: String, val `class`: KClass<*>) {

    sealed class Geometry(typeValue: String, `class`: KClass<*>) : GeoJsonType(typeValue,`class`) {

        companion object {
            val coordinatesKey = "coordinates"
        }

        object Point           : Geometry( typeValue = "Point"          , `class` = geojson.geometry.impl.Point          ::class)
        object MultiPoint      : Geometry( typeValue = "MultiPoint"     , `class` = geojson.geometry.impl.MultiPoint     ::class)
        object LineString      : Geometry( typeValue = "LineString"     , `class` = geojson.geometry.impl.LineString     ::class)
        object MultiLineString : Geometry( typeValue = "MultiLineString", `class` = geojson.geometry.impl.MultiLineString::class)
        object Polygon         : Geometry( typeValue = "Polygon"        , `class` = geojson.geometry.impl.Polygon        ::class)
        object MultiPolygon    : Geometry( typeValue = "MultiPolygon"   , `class` = geojson.geometry.impl.MultiPolygon   ::class)
    }

    object Feature : GeoJsonType(typeValue = "Feature", `class` = Feature::class) {
        val idKey         = "id"
        val propertiesKey = "properties"
        val geometryKey   = "geometry"
    }

    object FeatureCollection : GeoJsonType(typeValue = "FeatureCollection", `class` = FeatureCollection ::class) {
        val featuresKey      = "features"
        val totalFeaturesKey = "totalFeatures"
    }

    companion object {

        val typeKey = "type"

        val allTypes : Array<GeoJsonType> = arrayOf(
                GeoJsonType.Geometry.Point,
                GeoJsonType.Geometry.MultiPoint,
                GeoJsonType.Geometry.LineString,
                GeoJsonType.Geometry.MultiLineString,
                GeoJsonType.Geometry.Polygon,
                GeoJsonType.Geometry.MultiPolygon,
                GeoJsonType.Feature,
                GeoJsonType.FeatureCollection
        )

        fun forString( typeName: String )        : GeoJsonType? = allTypes.find { it.typeValue == typeName }
        fun forObject( `object`: GeoJsonObject ) : GeoJsonType? = allTypes.find { it.`class` == `object`::class }
    }
}



