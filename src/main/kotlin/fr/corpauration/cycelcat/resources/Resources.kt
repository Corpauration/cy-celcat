package fr.corpauration.cycelcat.resources

import kotlin.reflect.full.starProjectedType

class Resources {
    companion object {
        fun <E> valueOf(resType: E): Int {
            return when (resType.toString().split(".").last()) {
                "Student" -> 104
                else -> 0
            }
        }
    }
}