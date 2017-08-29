package geojson.geometry.impl

import geojson.geometry.Geometry
import geojson.geometry.MultiGeometry

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.7
 */
class MultiPolygon( coordinates: List<PolygonCoordinates> ) : Geometry<List<PolygonCoordinates>>( coordinates ), MultiGeometry<Polygon> {

    override fun split(): List<Polygon> = coordinates.map( ::Polygon )

    companion object : MultiGeometry.Companion<Polygon, PolygonCoordinates, MultiPolygon> {

        override fun join(geometries: List<Polygon>): MultiPolygon =
                MultiPolygon(coordinates = geometries.map { it.coordinates })

        override fun validateCoordinates(coordinates: List<PolygonCoordinates>) {
            coordinates.forEach( Polygon.Companion::validateCoordinates )
        }
    }
}