package sidev.lib.reflex.comp

import sidev.lib.reflex.core.ReflexFactory
import sidev.lib.reflex.core.ReflexTemplate


interface SiParameter: SiDescriptorContainer {
    val index: Int
    val name: String?
    val type: SiType
    val kind: Kind
    val isOptional: Boolean
    val isVararg: Boolean

    /** Property ini sementara hanya bisa didapatkan pada Js. */
    val defaultValue: Any?
        get()= null

    enum class Kind{
        /** Parameter yg menunjukan pemilik (instance) dari callable yg memiliki parameter ini. */
        INSTANCE,

        /** Instance yg digunakan untuk konteks `this` pada sebuah fungsi extension. */
        RECEIVER,

        /** Parameter yg memiliki nilai sebenarnya sesuai parameter yg dideklarasikan pada kode. */
        VALUE
    }
}

internal abstract class SiParamterImpl: SiDescriptorContainerImpl(), SiParameter{
//    abstract override var type: SiType
}

internal object SiParameterImplConst{
/*
    val nativeReceiver0: SiNativeParameter =
        NativeReflexFactory.createParameter(
            NativeReflexConst.NATIVE_PROPERTY_RECEIVER0_PARAMETER, 0, false, ReflexTemplate.typeAny
        )

    val nativeSetterValue: SiNativeParameter =
        NativeReflexFactory.createParameter(
            NativeReflexConst.NATIVE_PROPERTY_SETTER_VALUE_PARAMETER, 1, false, ReflexTemplate.typeAnyNullable //Untuk smtr tipenya ini.
        )
 */

    val receiver0: SiParameter = ReflexFactory.createParameter(
        null, null, 0, ReflexTemplate.typeAny, "\$receiver0"
    )
    val setterValue1: SiParameter = ReflexFactory.createParameter(
        null, null, 1, ReflexTemplate.typeAnyNullable, "\$setterValue"
    )
}

//class Poin()