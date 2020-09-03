package sidev.lib.reflex.comp.native

import sidev.lib.reflex.comp.SiParameter
import sidev.lib.reflex.comp.SiDescriptorContainerImpl


interface SiNativeParameter: SiNative, SiParameter {
    override val name: String
}

internal abstract class SiNativeParamterImpl: SiDescriptorContainerImpl(), SiNativeParameter{
    override val isVararg: Boolean get() = false
}

/*
internal object SiNativeParameterImplConst{
    val receiver0: SiNativeParameter = NativeReflexFactory.createParameter(
        NativeReflexConst.NATIVE_PROPERTY_RECEIVER0_PARAMETER, 0, false, ReflexTemplate.typeAny
    )
    val setterValue1: SiNativeParameter = NativeReflexFactory.createParameter(
        NativeReflexConst.NATIVE_PROPERTY_RECEIVER0_PARAMETER,1, false, ReflexTemplate.typeAnyNullable //Untuk smtr tipenya ini.
    )
}
 */