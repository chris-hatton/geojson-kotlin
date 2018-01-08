package geojson


data class FeatureCollection(
        val totalFeatures : Int,
        val features      : List<Feature>
) : GeoJsonObject()
