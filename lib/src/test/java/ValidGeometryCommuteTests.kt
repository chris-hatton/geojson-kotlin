import geojson.GeoJsonObject
import geojson.geometry.impl.*
import geojson.gson.GeoJsonType
import org.junit.Assert
import org.junit.Test

/**
 * Created by Chris on 14/08/2017.
 */
class ValidGeometryCommuteTests {

    @Test
    fun testCommutePoint() {
        val point = Point(latitude = 1.0, longitude = 2.0)
        testCommuteJson(geoJsonObject = point)
    }

    @Test
    fun testCommuteMultiPoint() {
        val multiPoint: MultiPoint = MultiPoint.join(
            Point.fromVertexPairs(1.1 to 1.2),
            Point.fromVertexPairs(1.3 to 1.4),
            Point.fromVertexPairs(1.5 to 1.6)
        )
        testCommuteJson(geoJsonObject = multiPoint)
    }

    @Test
    fun testCommuteLineString() {
        val lineString: LineString = LineString.fromVertexPairs(
            (0.0 to 0.0),
            (1.0 to 0.0),
            (1.0 to 1.0)
        )
        testCommuteJson(geoJsonObject = lineString)
    }

    @Test
    fun testCommuteMultiLineString() {
        val lineString1: LineString = LineString.fromVertexPairs(
            (0.0 to 0.0),
            (1.0 to 0.0),
            (1.0 to 1.0)
        )
        val lineString2: LineString = LineString.fromVertexPairs(
            (3.0 to 4.0),
            (4.0 to 2.0),
            (5.0 to 5.0)
        )
        val multiLineString: MultiLineString = MultiLineString.join(lineString1, lineString2)
        testCommuteJson(geoJsonObject = multiLineString)
    }

    @Test
    fun testCommutePolygon() {
        val polygon: Polygon = Polygon.fromVertexPairs(
            (0.0 to 0.0),
            (1.0 to 0.0),
            (1.0 to 1.0),
            (0.0 to 1.0)
        )
        testCommuteJson(geoJsonObject = polygon)
    }

    @Test
    fun testCommuteMultiPolygon() {
        val polygon1: Polygon = Polygon.fromVertexPairs(
            (0.0 to 0.0),
            (1.0 to 0.0),
            (1.0 to 1.0),
            (0.0 to 1.0)
        )
        val polygon2: Polygon = Polygon.fromVertexPairs(
            (2.0 to 2.0),
            (3.0 to 2.0),
            (3.0 to 3.0),
            (2.0 to 3.0)
        )
        val multiPolygon = MultiPolygon.join(polygon1, polygon2)
        testCommuteJson(geoJsonObject = multiPolygon)
    }

    private inline fun <reified T : GeoJsonObject> testCommuteJson(geoJsonObject: T) {
        val name = GeoJsonType.forObject(geoJsonObject)?.typeValue
        println("Converting $name to JSON...")
        val geoJsonText = geoJsonObject.toJson()
        println(geoJsonText)
        println("Converting JSON to $name...")
        val geoJsonObjectOut: T = GeoJsonObject.fromJson<T>(geoJsonText)
        println("Comparing...")
        Assert.assertEquals(geoJsonObject, geoJsonObjectOut)
        println("Success")
    }
}
