package sidev.lib.reflex.full

import sidev.lib.annotation.ChangeLog
import sidev.lib.check.asNotNull
import sidev.lib.check.notNullTo
import sidev.lib.collection.takeLast
import sidev.lib.console.prine
import sidev.lib.exception.NonInstantiableTypeExc
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.clazz
import sidev.lib.reflex.defaultPrimitiveValue
import sidev.lib.reflex.js.*
import sidev.lib.reflex.native_.*
import kotlin.reflect.KClass


/*
==========================
New Instance - Native
==========================
 */


actual fun <T: Any> T.nativeCloneOp(
    clonedObjOriginStack: MutableList<Any>, clonedObjStack: MutableList<Any>,
    isDeepClone: Boolean, constructorParamValFunc: ((KClass<*>, SiNativeParameter) -> Any?)?
): T{
    clonedObjOriginStack.indexOf(this).also { ind ->
        if(ind >= 0)
            return clonedObjStack[ind] as T
    }

    /**
     * Agar jml stack untuk obj asli [clonedObjOriginStack] dg hasil clone [clonedObjStack] sama.
     */
    fun T.preReturnObj(): T{
//        clonedObjStack!!.add(this)
        clonedObjOriginStack.takeLast()
        return this
    }
    clonedObjOriginStack.add(this)


    val clazz= this::class.also { if(it.isCopySafe) return this.preReturnObj() } as KClass<T>
    val valueMapTree= typedProperties

    val constructorPropertyList= mutableListOf<JsProperty<T, *>>()
    val newInsConstrParamValFunc= constructorParamValFunc ?: { clazz, param ->
        valueMapTree.find { (field, value) ->
            (param.name == null || param.name == field.name) && (
                    (value == null && param.type == Any::class)
                            || (value != null && param.type.simpleName == field.returnType.classifier?.name)
                    ) }
            .notNullTo {
                constructorPropertyList.add(it.first) //Agar loop for di bawah gak usah menyalin lagi property yg udah di-clone di konstruktor.
                if(!isDeepClone) it.second
                else it.second?.nativeCloneOp(clonedObjOriginStack, clonedObjStack, true, constructorParamValFunc)
            }
    }
    val newInsConstrParamValFuncWrapper= { paramOfNew: SiNativeParameter ->
        newInsConstrParamValFunc.invoke(clazz, paramOfNew)
    }

    val newInstance= when{
        @ChangeLog(
            "Selasa, 29 Sep 2020",
            """newInsConstrParamValFunc -> constructorParamValFunc agar valueMapTree induk tidak ikut,
                misal untuk kasus ArrayList, di mana valueMapTree-nya milik ArrayList, bkn elemennya"""
        )
        clazz.isArray -> return nativeArrayClone(isDeepClone, /*newInsConstrParamValFunc*/ constructorParamValFunc).preReturnObj() //as T
        clazz.isCollection -> return ((this as Collection<T>).nativeDeepClone(isDeepClone, /*newInsConstrParamValFunc*/ constructorParamValFunc) as T).preReturnObj()
        clazz.isMap -> return ((this as Map<*, T>).nativeDeepClone(isDeepClone, /*newInsConstrParamValFunc*/ constructorParamValFunc) as T).preReturnObj()
        else -> nativeNew(clazz, newInsConstrParamValFuncWrapper)
            ?: if(isDelegate) {
                prine("""This: "$this" merupakan delegate dan tidak tersedia nilai default untuk konstruktornya, return `this`.""")
                return this.preReturnObj() //Jika `this` merupakan built-in delegate yg gk bisa di-init, maka return this.
            } else throw NonInstantiableTypeExc(typeClass = this::class,
                msg = "Tidak tersedia nilai default untuk di-pass ke konstruktor.")
    }

    clonedObjStack.add(newInstance)

    for((field, value) in valueMapTree.filter { it.first !in constructorPropertyList }){
        if(value?.isUninitializedValue == true) continue
        field.asNotNull { mutableField: JsMutableProperty<T, Any?> ->
            if(!isDeepClone || value == null || value.clazz.isCopySafe || value.isReflexUnit){
//                if(constr.parameters.find { it.isPropertyLike((mutableField as SiField<*, *>).property, true) } == null)
                //<29 Agustus 2020> => filter kondisi if di atas tidak diperlukan karena udah dilakukan filter di awal saat di for.
                //Jika ternyata [mutableField] terletak di konstruktor dan sudah di-instansiasi,
                // itu artinya programmer sudah memberikan definisi nilainya sendiri saat intansiasi,
                // maka jangan salin nilai lama [mutableField] ke objek yg baru di-intansiasi.
                //<29 Agustus 2020> => Klarifikasi ttg filter if di atas.
                mutableField[newInstance]= value //value.withType(mutableField.returnType)
//                        mutableField.forcedSetTyped(newInstance, value.withType(mutableField.returnType))
            } else{
//                    mutableField.forcedSetTyped<T, Any?>(newInstance, value.clone(true, constructorParamValFunc).withType(mutableField.returnType))
                mutableField[newInstance]= value.nativeCloneOp(clonedObjOriginStack, clonedObjStack,true, constructorParamValFunc)
            }
        }
    }

    clonedObjOriginStack.takeLast()
    clonedObjStack.takeLast()

    return newInstance
}

actual fun <T: Any> nativeNew(clazz: KClass<T>, defParamValFunc: ((param: SiNativeParameter) -> Any?)?): T?{
    if(clazz.isCopySafe)
        return defaultPrimitiveValue(clazz)

    val constr= try{ clazz.jsClass }
    catch (e: ReflexComponentExc){
        prine("""nativeNew(): Tidak dapat meng-instansiasi kelas "$clazz" karena tidak tersedia fungsi konstruktor, return `null`.""")
        return null
    }
/*
    Pada Js, gakda private constructor.
    catch (e: Exception){
        prine("""nativeNew(): Tidak dapat meng-instansiasi kelas: "$clazz" karena tidak punya konstruktor publik, return `null`.""")
        return null
    }
 */
    val args= arrayListOf<Any?>()

    for(param in constr.parameters){
        args.add(
            defParamValFunc?.invoke(NativeReflexFactory._createNativeParameter(param))
                ?: param.defaultValue
                ?: param.type.classifier.notNullTo { defaultPrimitiveValue(it.kotlin) }
        )
    }

    return try{
        constr.new(*args.toTypedArray())
    } catch (e: Exception){
        prine("""nativeNew(): Tidak dapat meng-instansiasi kelas: "$clazz" karena tidak tersedianya argumen atau karena merupakan kelas yg tidak dapat di-init, return `null`.""")
        null
    }
}
