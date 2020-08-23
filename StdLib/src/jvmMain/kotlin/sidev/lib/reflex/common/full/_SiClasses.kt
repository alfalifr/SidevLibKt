@file:JvmName("SiClassesKtJvm")

package sidev.lib.reflex.common.full

import sidev.lib.console.prine
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.InnerReflex
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiFunction
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Type
import kotlin.reflect.*
import kotlin.reflect.full.primaryConstructor


actual val SiClass<*>.isPrimitive: Boolean get()= when(val native= descriptor.native){
    is KClass<*> -> native.javaPrimitiveType != null
    is Class<*> -> native.isPrimitive
    else -> throw ReflexComponentExc(currentReflexedUnit = native ?: "<null>", detMsg = "`this` bkn merupakan golongan Class.")
}

actual val SiClass<*>.isObjectArray: Boolean get()= when(val native= descriptor.native){
    is KClass<*> -> native.toString() == InnerReflex.K_ARRAY_CLASS_STRING
    is Class<*> -> native.isArray && native.componentType?.isPrimitive?.not() == true
    else -> throw ReflexComponentExc(currentReflexedUnit = native ?: "<null>", detMsg = "`this` bkn merupakan golongan Class.")
}

actual val SiClass<*>.isPrimitiveArray: Boolean get()= when(val native= descriptor.native){
    is KClass<*> -> when(native){
        IntArray::class -> true
        LongArray::class -> true
        FloatArray::class -> true
        DoubleArray::class -> true
        CharArray::class -> true
        ShortArray::class -> true
        BooleanArray::class -> true
        ByteArray::class -> true
        else -> false
    }
    is Class<*> -> native.isArray && native.componentType?.isPrimitive == true
    else -> throw ReflexComponentExc(currentReflexedUnit = native ?: "<null>", detMsg = "`this` bkn merupakan golongan Class.")
}

actual val Any.isNativeReflexUnit: Boolean get()= when(this){
    is KParameter -> true
    is KCallable<*> -> true
    is KClass<*> -> true
    is KType -> true
    is KTypeParameter -> true
    is KClassifier -> true

    is AccessibleObject -> true
    is Type -> true

    else -> false
}


actual val <T: Any> SiClass<T>.primaryConstructor: SiFunction<T> get() = when(val native= descriptor.native){
    is KClass<*> -> {
        val nativeConstr= native.primaryConstructor
        constructors.find { it.descriptor.native == nativeConstr }!!
    }
    else -> throw ReflexComponentExc(currentReflexedUnit = this::class, detMsg = "Kelas native: \"$this\" bkn kelas.")
}