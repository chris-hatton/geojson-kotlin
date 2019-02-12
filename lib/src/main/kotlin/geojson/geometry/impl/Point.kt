package geojson.geometry.impl

import geojson.Exception
import geojson.Position
import geojson.geometry.Geometry

typealias PointCoordinates = Position

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.2
 */
class Point( coordinates: PointCoordinates) : Geometry<PointCoordinates>( coordinates ) {

    /**
     * Convenience constructor, forming the mandated 'single Position' coordinate from Latitude
     * and Longitude components.
     */
    constructor( longitude : Double, latitude: Double) : this( Position( longitude = longitude, latitude = latitude ) )

    /** Convenience accessor for the Longitude component of this [Point]s coordinate. */
    val longitude : Double = coordinates.longitude

    /** Convenience accessor for the Latitude component of this [Point]s coordinate. */
    val latitude  : Double = coordinates.latitude

    override fun toString(): String = "POINT(${coordinates.latitude}%20$${coordinates.longitude})"

    companion object : Geometry.Companion.Open<Point, Position> {

        override fun fromPositions( positions: List<Position>): Point {
            return when( positions.count() ) {
                1    -> Point( coordinates = positions[0] )
                else -> throw Exception.IllegalFormat("When creating a Point from a vertex list, only ONE element is allowed.")
            }
        }

        override fun fromCoordinates(coordinates: Position): Point = Point(coordinates)

        override fun validateCoordinates(coordinates: Position) {
            coordinates.validate()
        }

        /* The most South-Westerly point. */
        val minimum = Point( longitude = -180.0, latitude = -90.0 )

        /* The most North-Easterly point. */
        val maximum = Point( longitude =  180.0, latitude =  90.0 )
    }
}