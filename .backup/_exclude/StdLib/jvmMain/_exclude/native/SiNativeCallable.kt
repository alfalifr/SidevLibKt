package sidev.lib.reflex.comp.native


interface SiNativeCallable<out R>: SiNative {
    val parameters: List<SiNativeParameter>
    fun call(vararg args: Any?): R
}

//internal abstract class SiNativeCallableImpl<out R>: SiReflexImpl(), SiNativeCallable<R>