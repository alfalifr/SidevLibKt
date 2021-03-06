@file:JvmName("_SiPropertyFunJvm")

package sidev.lib.reflex.full

import sidev.lib.property.SI_UNINITIALIZED_VALUE
import sidev.lib.reflex.SiProperty1
import java.lang.Error
import java.lang.reflect.InvocationTargetException


internal actual fun <T, V> SiProperty1<T, V>.handleNativeForceGet(receiver: T, exceptionFromCommon: Throwable): V = when(exceptionFromCommon){
    is InvocationTargetException -> when(exceptionFromCommon.cause){
        is UninitializedPropertyAccessException -> SI_UNINITIALIZED_VALUE
        else -> throw exceptionFromCommon
    } as V
    is Error -> when(exceptionFromCommon.message){
        "No accessor found for property val kotlin.Array<T>.size: kotlin.Int" -> (receiver as Array<*>).size
        else -> throw exceptionFromCommon
    } as V
    else -> throw exceptionFromCommon
}