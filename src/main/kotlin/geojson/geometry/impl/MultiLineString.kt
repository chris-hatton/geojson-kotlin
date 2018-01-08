package geojson.geometry.impl

import geojson.geometry.Geometry
import geojson.geometry.MultiGeometry

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.7
 */
class MultiLineString( coordinates: List<LineStringCoordinates> ) : Geometry<List<LineStringCoordinates>>( coordinates ), MultiGeometry<LineString> {

    override fun split(): List<LineString> = coordinates.map( ::LineString )

    companion object : MultiGeometry.Companion<LineString, LineStringCoordinates, MultiLineString> {

        override fun join(geometries: List<LineString>): MultiLineString =
                MultiLineString(coordinates = geometries.map { it.coordinates })

        override fun validateCoordinates(coordinates: List<LineStringCoordinates>) {
            coordinates.forEach( LineString.Companion::validateCoordinates )
        }
    }
}