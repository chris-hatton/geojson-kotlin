package geojson.geometry.impl

import geojson.Exception
import geojson.Position
import geojson.geometry.Geometry

typealias LineStringCoordinates = List<Position>

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.4
 */
class LineString( coordinates: LineStringCoordinates) : Geometry<LineStringCoordinates>( coordinates ) {

    companion object : Geometry.Companion.Open<LineString, LineStringCoordinates> {

        override fun fromCoordinates(coordinates: LineStringCoordinates): LineString = LineString(coordinates)

        override fun fromPositions(positions: List<Position>): LineString = fromCoordinates( positions )

        override fun validateCoordinates(coordinates: List<Position>) {
            if( coordinates.count() < 2 ) {
                throw Exception.IllegalFormat("A LineString must have at least two positions.")
            }
        }
    }
}