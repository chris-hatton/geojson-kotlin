package geojson

/**
 * https://tools.ietf.org/html/rfc7946#section-3.1.1
 */
data class Position(val longitude: Double, val latitude: Double, val altitude: Double? = null) {
    fun validate() {
        if (longitude < -180.0 || longitude > 180.0) {
            throw Exception.IllegalFormat("Longitude '$longitude' is out of range -180 to 180")
        }
        if (latitude < -90.0 || latitude > 90.0) {
            throw Exception.IllegalFormat("Latitude '$latitude' is out of range -90 to 90")
        }
    }

    val hasAltitude: Boolean get() = altitude != null
}