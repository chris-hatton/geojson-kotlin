package geojson.geometry.impl

import geojson.Exception
import geojson.LinearRing
import geojson.Position
import geojson.geometry.Geometry

typealias PolygonCoordinates = List<List<Position>>

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.6
 */
class Polygon internal constructor( coordinates: PolygonCoordinates) : Geometry<PolygonCoordinates>( coordinates ) {

    companion object : Geometry.Companion.Closed<Polygon, PolygonCoordinates> {

        override fun fromCoordinates(coordinates: PolygonCoordinates): Polygon = Polygon(coordinates)

        override fun fromPositions(positions: List<Position>): Polygon = fromCoordinates( listOf( positions ) )

        override fun validateCoordinates(coordinates: PolygonCoordinates) {

            coordinates.withIndex().find { (_,positions) -> !LinearRing.isLinearRing(positions) }?.let {
                (index,positions) ->
                throw Exception.IllegalFormat("The top-level element at index [$index] of this Polygon's coordinates does not meet Linear Ring criteria, they are: '$positions'")
            }

            // TODO: Check that first Linear Ring is outer, encompassing all subsequent rings.
        }
    }
}

