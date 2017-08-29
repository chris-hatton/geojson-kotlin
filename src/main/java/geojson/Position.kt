package geojson

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.1
 */
data class Position(val longitude: Double, val latitude: Double, val altitude: Double? = null ) {
    fun validate() {
        if( longitude !in -180..180 ) { throw Exception.IllegalFormat("Longitude '$longitude' is out of range -180 to 180") }
        if( latitude  !in -90..90   ) { throw Exception.IllegalFormat("Latitude '$latitude' is out of range -90 to 90") }
    }

    val hasAltitude : Boolean get() = altitude != null
}