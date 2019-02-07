package geojson

/**
 * Created by Chris on 17/08/2017.
 */
fun List<Position>.checkAltitudeConsistent() {
    if( isEmpty() ) { return }
    val hasAltitude = this[0].hasAltitude
    val isAltitudeConsistent = this.drop(1).all { it.hasAltitude == hasAltitude }
    if( !isAltitudeConsistent ) { throw Exception.InconsistentPositionDimensions }
}