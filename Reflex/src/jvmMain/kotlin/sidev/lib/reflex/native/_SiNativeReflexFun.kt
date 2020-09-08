@file:JvmName("_SiNativeReflexFunJvm")

package sidev.lib.reflex.native

import sidev.lib.check.asNotNull
import sidev.lib.exception.ReflexComponentExc
import java.lang.reflect.Parameter
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

internal actual fun getSiNativeParameter(nativeParameter: Any): SiNativeParameter {
    var clazz: KClass<*> = Any::class
    var isGeneric= true
    val name= when(nativeParameter){
        is Parameter -> {
            clazz= nativeParameter.type.kotlin
            isGeneric= clazz == Any::class //Anggapannya jika tipenya merupakan Object, maka langsung generic.
            nativeParameter.name
        }
        is KParameter -> {
            nativeParameter.type.classifier.asNotNull { cls: KClass<*> ->
                clazz= cls
                isGeneric= false
            }
            nativeParameter.name
        }
        else -> throw ReflexComponentExc(currentReflexedUnit = nativeParameter, detMsg = """nativeParameter: "$nativeParameter" bkn merupakan native parameter pada Jvm.""")
    }
    return object : SiNativeParameter {
        override val implementation: Any = nativeParameter
        override val name: String = name ?: NativeReflexConst.TEMPLATE_NATIVE_NAME
        override val type: KClass<*> = clazz
        override val isGeneric: Boolean = isGeneric
    }
}