package geojson.geometry

import geojson.Position
import geojson.geometry.impl.LineString
import geojson.geometry.impl.LineStringCoordinates

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.5
 */
class MultiLineString( coordinates: List<LineStringCoordinates> ) : Geometry<List<LineStringCoordinates>>( coordinates ), MultiGeometry<LineString> {

    override fun split(): List<LineString> = coordinates.map( ::LineString )

    companion object : MultiGeometry.Companion<LineString,LineStringCoordinates,MultiLineString> {

        override fun join(geometries: List<LineString>): MultiLineString {
            return MultiLineString( coordinates = geometries.map { it.coordinates } )
        }

        /** Each child component of the [MultiLineString] must be [LineString] compliant. */
        override fun validateCoordinates(coordinates: List<List<Position>>) {
            coordinates.forEach( LineString.Companion::validateCoordinates )
        }
    }
}