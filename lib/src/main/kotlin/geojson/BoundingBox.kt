package geojson

import geojson.geometry.impl.Point

/**
 * Although in broad conceptual terms, a 'Bounding Box' is a geometry, under the GeoJSON
 * specification a bounding box is not classified as a such.
 *
 * A bounding box in GeoJSON is used only to refine requests for geo-spatial data to a targeted
 * region, rather than in specifying features having box-like geometry.
 *
 * https://tools.ietf.org/html/rfc7946#section-5
 */
data class BoundingBox(
        val southWest : Point,
        val northEast : Point
) {
    constructor( west: Double, south: Double, east: Double, north: Double ) : this( Point(west,south), Point(east,north) )

    override fun toString(): String {
        // left,lower,right,upper,crs
        return "${southWest.longitude},${southWest.latitude},${northEast.longitude},${northEast.latitude}"
    }

    companion object {

        /* A bounding-box which encompasses the entire globe, from the most south-westerly to the most north-easterly point. */
        var Global: BoundingBox = BoundingBox( southWest = Point.minimum, northEast = Point.maximum)
    }
}