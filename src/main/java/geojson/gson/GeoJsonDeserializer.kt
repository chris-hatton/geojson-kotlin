package geojson.gson

import com.google.gson.*
import geojson.Feature
import geojson.FeatureCollection
import geojson.GeoJsonObject
import geojson.Position
import geojson.geometry.Geometry
import geojson.geometry.MultiLineString
import geojson.geometry.impl.*
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class GeoJsonDeserializer<T: GeoJsonObject> : JsonDeserializer<T> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): T {

        val geoJsonType : GeoJsonType = readType( json.asJsonObject )
        
        return when( geoJsonType ) {
            GeoJsonType.Feature           -> readFeature          (json)             as T
            GeoJsonType.FeatureCollection -> readFeatureCollection(json)             as T
            is GeoJsonType.Geometry       -> readGeometry         (json,geoJsonType) as T
        }
    }

    private fun readType( jsonObject: JsonObject ) : GeoJsonType {
        val typeName : String = jsonObject.get(GeoJsonType.typeKey).asString
        return GeoJsonType.forString( typeName ) ?: throw JsonParseException("Unrecognised geometry type '$typeName'")
    }

    private fun readPosition( element: JsonElement) : Position {
        val array = element.asJsonArray ?: throw geojson.Exception.IllegalFormat()
        if( array.size() < 2 ) throw geojson.Exception.IllegalFormat("A GeoJSON Position must have at least two elements")
        val longitude : Double  = array.get(0)?.asNumber?.toDouble() ?: throw geojson.Exception.IllegalFormat()
        val latitude  : Double  = array.get(1)?.asNumber?.toDouble() ?: throw geojson.Exception.IllegalFormat()
        val altitude  : Double? = if( array.size() >= 3 ) array.get(2).asNumber?.toDouble() else null
        return Position(longitude,latitude,altitude)
    }

    private fun readListOfPositions( element: JsonElement) : List<Position> {
        val array = element.asJsonArray ?: throw geojson.Exception.IllegalFormat()
        return array.map { readPosition(it) }
    }

    private fun readListOfListOfPositions( element: JsonElement) : List<List<Position>> {
        val array = element.asJsonArray ?: throw geojson.Exception.IllegalFormat()
        return array.map { readListOfPositions(it) }
    }

    private fun readListOfListOfListOfPositions( element: JsonElement) : List<List<List<Position>>> {
        val array = element.asJsonArray ?: throw geojson.Exception.IllegalFormat()
        return array.map { readListOfListOfPositions(it) }
    }

    private fun readCoordinates( element: JsonElement) : JsonElement =
        (element as? JsonObject)?.get(GeoJsonType.Geometry.coordinatesKey) ?: throw geojson.Exception.IllegalFormat()

    private fun readPoint          ( element: JsonElement) : Point              = Point           (coordinates = readPosition                     (readCoordinates(element)))
    private fun readMultiPoint     ( element: JsonElement) : MultiPoint         = MultiPoint      (coordinates = readListOfPositions              (readCoordinates(element)))
    private fun readLineString     ( element: JsonElement) : LineString         = LineString      (coordinates = readListOfPositions              (readCoordinates(element)))
    private fun readMultiLineString( element: JsonElement) : MultiLineString    = MultiLineString (coordinates = readListOfListOfPositions        (readCoordinates(element)))
    private fun readPolygon        ( element: JsonElement) : Polygon            = Polygon         (coordinates = readListOfListOfPositions        (readCoordinates(element)))
    private fun readMultiPolygon   ( element: JsonElement) : MultiPolygon       = MultiPolygon    (coordinates = readListOfListOfListOfPositions  (readCoordinates(element)))

    private fun readGeometry(
            element      : JsonElement,
            geometryType : GeoJsonType.Geometry = readType( element.asJsonObject ) as? GeoJsonType.Geometry ?: throw Exception()
    ) : Geometry<*> = when( geometryType ) {
        GeoJsonType.Geometry.Point            -> readPoint          (element) 
        GeoJsonType.Geometry.MultiPoint       -> readMultiPoint     (element) 
        GeoJsonType.Geometry.LineString       -> readLineString     (element) 
        GeoJsonType.Geometry.MultiLineString  -> readMultiLineString(element) 
        GeoJsonType.Geometry.Polygon          -> readPolygon        (element) 
        GeoJsonType.Geometry.MultiPolygon     -> readMultiPolygon   (element) 
    }

    private fun readFeature( element: JsonElement) : Feature {
        val jsonObject = element.asJsonObject ?: throw geojson.Exception.IllegalFormat()
        val id : String? = jsonObject.getAsJsonPrimitive(GeoJsonType.Feature.idKey)?.asString
        val geometryObject : JsonObject = jsonObject.getAsJsonObject( GeoJsonType.Feature.geometryKey )
        val geometry : Geometry<*> = readGeometry( geometryObject )
        val properties = jsonObject.getAsJsonObject( GeoJsonType.Feature.propertiesKey )
        return Feature( id, geometry, properties )
    }

    private fun readFeatureCollection( element: JsonElement) : FeatureCollection {
        val jsonObject    : JsonObject = element.asJsonObject ?: throw geojson.Exception.IllegalFormat()
        val featuresArray : JsonArray = jsonObject.get(GeoJsonType.FeatureCollection.featuresKey)?.asJsonArray ?: throw geojson.Exception.IllegalFormat()
        val totalFeatures : Int = jsonObject.get(GeoJsonType.FeatureCollection.totalFeaturesKey)?.asInt ?: throw geojson.Exception.IllegalFormat()
        val features = featuresArray.map { readFeature(it) }
        return FeatureCollection( totalFeatures = totalFeatures, features = features )
    }
}