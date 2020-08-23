package sidev.lib.reflex.common.full.types

import sidev.lib.reflex.common.SiType
import kotlin.reflect.KType

/**
 * Digunakan untuk menyimpan value beserta type-nya.
 */
data class TypedValue<T>(val type: SiType, val value: T)

fun <T> T.withType(type: SiType): TypedValue<T> = TypedValue(type, this)
//fun TypedValue<*>.component1() = type