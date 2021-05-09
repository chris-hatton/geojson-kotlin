package geojson

import kotlin.reflect.KClass

sealed class Exception : kotlin.Exception() {
    data class IllegalFormat(val reason: String? = null) : Exception()
    data class UnsupportedType(val type: KClass<*>) : Exception()
    data class UnknownTypeName(val name: String) : Exception()
    object InconsistentPositionDimensions : Exception()
}
