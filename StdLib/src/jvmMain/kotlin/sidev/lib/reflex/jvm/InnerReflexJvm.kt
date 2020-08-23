package sidev.lib.reflex.jvm

import sidev.lib.console.prine
import java.lang.reflect.Constructor
import java.lang.reflect.Executable
import java.lang.reflect.Method
import kotlin.math.ceil
import kotlin.reflect.KCallable
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

object InnerReflexJvm{
    const val K_CLASS_BASE_NAME= "KClassImpl"
    const val K_FUNCTION_CONSTRUCTOR_NAME_PREFIX= "fun <init>"
    const val K_FUNCTION_CONSTRUCTOR_NAME= "<init>"
    const val K_DEFAULT_FUNCTION_NAME_SUFFIX= "\$default"
    val K_PROPERTY_ARRAY_SIZE_STRING= Array<Any>::size.toString()
    val K_CLASS_ENUM_STRING= Enum::class
    val K_ARRAY_CLASS_STRING: String = Array<Any>::class.toString()


    fun isDefaultOfFun(javaMethod: Executable, kotlinFun: KCallable<*>): Boolean{
        val kotlinParam= kotlinFun.parameters
        val kotlinOptionalParam= kotlinParam.filter { it.isOptional }
            .also { if(it.isEmpty()) return false } //Karena method yg gak punya param opsional brarti gak punya fungsi default.

        val maskParamCount= ceil(kotlinOptionalParam.size / 32.0).toInt()
        val isNameMatched= if(javaMethod is Method) javaMethod.name.endsWith(K_DEFAULT_FUNCTION_NAME_SUFFIX)
            else true

        val bool= isNameMatched
                && javaMethod.isSynthetic
                && javaMethod.parameterCount == (kotlinParam.size + maskParamCount + 1) //+1 digunakan untuk tambahan param marker pada fungsi default di kotlin.
                && {
                    var isTypeSame= true
                    javaMethod.parameterTypes.forEachIndexed { i, javaType ->
                        if(i >= kotlinParam.size)
                            return@forEachIndexed
                        if(javaType.kotlin != kotlinParam[i].type.classifier){
                            isTypeSame= false
                            return@forEachIndexed
                    } }
                    isTypeSame
                }()
        prine("isDefaultOfFun() javaMethod= $javaMethod kotlinFun= $kotlinFun bool= $bool")
        return bool
//                && javaMethod.parameterTypes.last() == java.lang.Object::class //Gak bisa dicek tipe data parameter yg trahir kalau constructor
    }
/*
    fun <T> isDefaultOfConst(javaConstr: Constructor<T>, kotlinFun: KCallable<T>): Boolean{
        val kotlinParam= kotlinFun.parameters
        val kotlinOptionalParam= kotlinParam.filter { it.isOptional }
            .also { if(it.isEmpty()) return false } //Karena method yg gak punya param opsional brarti gak punya fungsi default.

        val maskParamCount= ceil(kotlinOptionalParam.size / 32.0).toInt()
//        DefaultConstructorMarker
        return javaConstr.name.endsWith(K_DEFAULT_FUNCTION_NAME_SUFFIX)
                && javaConstr.isSynthetic
                && javaConstr.parameterCount == (kotlinParam.size + maskParamCount + 1) //+1 digunakan untuk tambahan param marker pada fungsi default di kotlin.
    }
 */
}