package geojson.geometry

/**
 * GeoJSON (RFC7946) specifies several 'Multi-' geometry types whose coordinates are a
 * straightforward collection of the coordinates of their associated, singular type.  These are:
 *
 * MultiPoint      -> Point
 * MultiLineString -> LineString
 * MultiPolygon    -> Polygon
 *
 * For convenience sake; this library identifies these types with the MultiGeometry interface,
 * which guarantees the provision of a function to split out a collection of base types from the
 * Multi type.
 *
 * @param SG Singular geometry type
 */
interface MultiGeometry<out SG> {

    /**
     * Return a collection of singular geometry instances, split from this multi-geometry instance.
     */
    fun split() : List<SG>

    /**
     * Interface which, by convention alone, the 'companion object' of multi-geometry classes
     * should implement.
     *
     * @param SG Singular geometry type
     * @param SGC Singular geometry coordinates type
     * @param MG Multi-geometry type
     */
    interface Companion<in SG, in SGC, out MG> {

        fun join( geometries: List<SG> ) : MG

        fun join( vararg geometries: SG ) = join( geometries.toList() )

        fun validateCoordinates( coordinates: List<SGC> )
    }
}

