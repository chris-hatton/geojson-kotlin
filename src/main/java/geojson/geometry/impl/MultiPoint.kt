package geojson.geometry.impl

import geojson.Exception
import geojson.Position
import geojson.checkAltitudeConsistent
import geojson.geometry.Geometry
import geojson.geometry.MultiGeometry

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.3
 */
class MultiPoint( coordinates: List<PointCoordinates> ) : Geometry<List<PointCoordinates>>( coordinates ), MultiGeometry<Point> {

    override fun split(): List<Point> = coordinates.map( ::Point )

    companion object : MultiGeometry.Companion<Point, PointCoordinates, MultiPoint> {

        override fun join(geometries: List<Point>): MultiPoint =
                MultiPoint(coordinates = geometries.map { it.coordinates })

        override fun validateCoordinates(coordinates: List<Position>) {
            coordinates.checkAltitudeConsistent()
        }
    }
}