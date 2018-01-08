package geojson

/**
 * RF7646 discusses the concept of a Linear Ring in section 3.1.6:
 * https://tools.ietf.org/html/rfc7946#section-3.1.6
 *
 * A Linear Ring is not explicitly represented as a GeoJSON geometry type,
 * instead being a sequence of positions with certain properties.
 *
 * Maintaining consistency with RF7646, this library will also avoid an object
 * representation of LinearRing, instead providing functions to manipulate
 * sequences of positions with respect to the Linear Ring definition.
 */
object LinearRing {

    /**
     * Determine if this sequence of Positions meets the criteria of being a Linear Ring.
     */
    fun isLinearRing( positions: List<Position> ) : Boolean {
        return with( positions ) {
            // Position-count and start-end sameness checked.  TODO: Check winding?
            ( count() >= 4 ) && ( first() == last() )
        }
    }

    fun fromVertices( positions: List<Position> ) : List<Position> {
        return with( positions ) {
            if( count() >= 3 ) {
                this + first()
            } else {
                throw Exception.InsufficientPositions
            }
        }
    }

    sealed class Exception : kotlin.Exception() {
        object InsufficientPositions : Exception()
    }
}

