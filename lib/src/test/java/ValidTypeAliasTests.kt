import geojson.Feature
import geojson.FeatureCollection
import geojson.GeoJsonObject
import geojson.geometry.impl.Point
import geojson.gson.GeoJsonType
import org.junit.Assert
import org.junit.Test

typealias Where = FeatureCollection

/**
 * Created by Chris on 14/08/2017.
 */
class ValidTypeAliasTests {


    @Test
    fun testFeatureCollection() {
        val point = Point(latitude = 1.0, longitude = 2.0)
        val feature = Feature(geometry = point)
        val collection = FeatureCollection(1, listOf(feature))

        testCommuteJson(geoJsonObject = collection)
    }

    @Test
    fun testWhere() {
        val point = Point(latitude = 1.0, longitude = 2.0)
        val feature = Feature(geometry = point)
        val collection = Where(1, listOf(feature))

        testCommuteJson(geoJsonObject = collection)
    }


    private inline fun <reified T : GeoJsonObject> testCommuteJson(geoJsonObject: T) {
        val name = GeoJsonType.forObject(geoJsonObject)?.typeValue
        println("Converting $name to JSON...")
        val geoJsonText = geoJsonObject.toJson()
        println(geoJsonText)
        println("Converting JSON to $name...")
        val geoJsonObjectOut: T = GeoJsonObject.fromJson(geoJsonText)
        println("Comparing...")
        Assert.assertEquals(geoJsonObject, geoJsonObjectOut)
        println("Success")
    }
}