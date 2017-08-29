package geojson.geometry

import geojson.GeoJsonObject
import geojson.Position
import geojson.geometry.impl.LineString

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1
 */
abstract class Geometry<out C>(val coordinates: C ) : GeoJsonObject() {

    interface Companion<out G: Geometry<C>,C> {

        /**
         * If the coordinates are valid for Geometry G, this function has no effect.
         * If they are invalid, an exception will be thrown.
         */
        fun validateCoordinates( coordinates: C )

        fun fromVertices( vertexPositions: List<Position> ) : G

        fun fromPositions( positions: List<Position> ) : G

        fun fromCoordinates( coordinates: C ) : G

        fun fromVertexPairs( vararg vertexPairs: Pair<Double,Double> ) : G {
            val vertices : List<Position> = vertexPairs.map { Position( it.first, it.second ) }
            return fromVertices(vertices)
        }

        interface Closed<out G: Geometry<C>,C> : Companion<G,C> {
            override fun fromVertices( vertexPositions: List<Position> ) : G {
                val positions : List<Position> = if(vertexPositions.isEmpty()) emptyList() else vertexPositions + vertexPositions[0]
                return fromPositions( positions )
            }
        }

        interface Open<out G: Geometry<C>,C> : Companion<G,C> {
            override fun fromVertices( vertexPositions: List<Position> ) : G = fromPositions( vertexPositions )
        }
    }

//    internal interface InternalCompanion<out G:Geometry<C>,C> {
//        fun fromUnsafeCoordinates( coordinates: C ) : G
//    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Geometry<*>

        if (coordinates != other.coordinates) return false

        return true
    }

    override fun hashCode(): Int {
        return coordinates?.hashCode() ?: 0
    }
}