package geojson.gson

import com.google.gson.*
import geojson.Exception
import geojson.Feature
import geojson.FeatureCollection
import geojson.GeoJsonObject
import geojson.Position
import geojson.geometry.Geometry
import geojson.geometry.MultiLineString
import geojson.geometry.impl.*
import java.lang.reflect.Type

//@Suppress("UNCHECKED_CAST")
class GeoJsonSerializer<T: GeoJsonObject> : JsonSerializer<T> {

    override fun serialize(src: T, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {

        val jsonObject : JsonObject = when( src ) {
            is Feature           -> writeFeature(src)
            is FeatureCollection -> writeFeatureCollection(src)
            is Geometry<*>       -> writeGeometry(src)
            else -> throw Exception.UnsupportedType(src::class)
        }

        val geoJsonType : GeoJsonType = GeoJsonType.forObject(src) ?: throw geojson.Exception.UnsupportedType( src::class )
        jsonObject.addProperty( GeoJsonType.typeKey, geoJsonType.typeValue )

        return jsonObject
    }

    private fun writePosition( coordinates: Position) : JsonArray {
        return JsonArray().apply {
            add( coordinates.longitude )
            add( coordinates.latitude  )
            coordinates.altitude?.let( this::add )
        }
    }

    private fun writeListOfPositions( coordinates: List<Position> ) : JsonArray =
        JsonArray().apply { coordinates.map( this@GeoJsonSerializer::writePosition ).forEach( this::add ) }

    private fun writeListOfListOfPositions( coordinates: List<List<Position>>) : JsonArray =
        JsonArray().apply { coordinates.map( this@GeoJsonSerializer::writeListOfPositions ).forEach( this::add ) }

    private fun writeListOfListOfListOfPositions( coordinates: List<List<List<Position>>> ) : JsonArray =
        JsonArray().apply { coordinates.map(this@GeoJsonSerializer::writeListOfListOfPositions).forEach(this::add) }

    private fun writeGeometry( geometry: Geometry<*> ) : JsonObject {

        val coordinatesArray : JsonArray = when( geometry ) {
            is Point           -> writePosition                   (geometry.coordinates)
            is MultiPoint      -> writeListOfPositions            (geometry.coordinates)
            is LineString      -> writeListOfPositions            (geometry.coordinates)
            is MultiLineString -> writeListOfListOfPositions      (geometry.coordinates)
            is Polygon         -> writeListOfListOfPositions      (geometry.coordinates)
            is MultiPolygon    -> writeListOfListOfListOfPositions(geometry.coordinates)
            else               -> throw geojson.Exception.UnsupportedType(geometry::class)
        }

        return JsonObject().apply {
            add( GeoJsonType.Geometry.coordinatesKey, coordinatesArray )
        }
    }

    private fun writeFeature(feature: Feature ) : JsonObject = JsonObject().apply {
        addProperty(GeoJsonType.Feature.idKey, feature.id)
        add( GeoJsonType.Feature.geometryKey, writeGeometry( feature.geometry ) )

        val jsonTree = Gson().toJsonTree(feature.properties)

        add( GeoJsonType.Feature.propertiesKey, jsonTree )
    }

    private fun writeFeatureCollection(featureCollection: FeatureCollection ) : JsonObject {

        val featuresArray : JsonArray = JsonArray().apply {
            featureCollection.features.map( this@GeoJsonSerializer::writeFeature ).forEach(this::add)
        }

        return JsonObject().apply {
            add( GeoJsonType.FeatureCollection.featuresKey, featuresArray )
            addProperty( GeoJsonType.FeatureCollection.totalFeaturesKey, featureCollection.totalFeatures )
        }
    }
}