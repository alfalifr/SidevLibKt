package sidev.lib.reflex.full

import sidev.lib.check.asNotNullTo
import sidev.lib.check.notNull
import sidev.lib.check.notNullTo
import sidev.lib.collection.sequence.nestedSequence
import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiField
import sidev.lib.reflex.SiMutableField
import sidev.lib.reflex.SiMutableProperty1
import sidev.lib.reflex.SiProperty1
import sidev.lib.reflex.native_.getIsAccessible
import sidev.lib.reflex.native_.setIsAccessible
import sidev.lib.reflex.si
import sidev.lib.collection.sequence.NestedSequence
import kotlin.jvm.JvmName


@set:JvmName("setAccessible")
@get:JvmName("getAccessible")
var SiField<*, *>.isAccessible: Boolean
    get()= descriptor.native.notNullTo { getIsAccessible(it) } ?: false
    set(v){ descriptor.native.notNull { setIsAccessible(it, v) } }

fun <T: Any, R> SiField<T, R>.forceGet(receiver: T): R {
    val initAccessible= isAccessible
    isAccessible= true
    val vals= get(receiver) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
    return vals
}

fun <T: Any, R> SiMutableField<T, R>.forceSet(receiver: T, value: R) {
    val initAccessible= isAccessible
    isAccessible= true
    set(receiver, value) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
}

@get:JvmName("getProperty")
val <T, R> SiField<T, R>.property: SiProperty1<T, R>
    get()= descriptor.host as SiProperty1<T, R>

@get:JvmName("getProperty")
val <T, R> SiMutableField<T, R>.property: SiMutableProperty1<T, R>
    get()= descriptor.host as SiMutableProperty1<T, R>


@get:JvmName("declaredFields")
val <T: Any> SiClass<T>.declaredFields: Sequence<SiField<T, *>>
    get()= declaredMemberProperties.mapNotNull { it.backingField }

@get:JvmName("declaredFieldsTree")
val <T: Any> SiClass<T>.declaredFieldsTree: NestedSequence<SiField<T, *>>
    get()= nestedSequence(classesTree){ cls: SiClass<*> -> cls.declaredFields.iterator() }
            as NestedSequence<SiField<T, *>>

@get:JvmName("nestedDeclaredFieldsTree")
val SiClass<*>.nestedDeclaredFieldsTree: NestedSequence<SiField<Any, *>>
    get()= nestedSequence<SiClass<*>, SiField<Any, *>>(classesTree, {
        it.type.classifier.asNotNullTo { cls: SiClass<*> -> cls.classesTree.iterator() }
    })
    { cls: SiClass<*> -> cls.declaredFields.iterator() as Iterator<SiField<Any, *>> } //as NestedSequence<SiProperty<T, *>>

/*
<23 Agustus 2020> => Gakda field yg abstrak.
val SiClass<*>.implementedFieldsTree: Sequence<SiField<*, *>>
    get()= implementedMemberPropertiesTree.mapNotNull { it.backingField }

val SiClass<*>.implementedNestedFieldsTree: Sequence<SiField<*, *>>
    get()= implementedNestedMemberPropertiesTree.mapNotNull { it.backingField }
 */

@get:JvmName("fieldValues")
val <T: Any> T.fieldValues: Sequence<Pair<SiField<T, *>, Any?>>
    get(){
        return this::class.si.declaredFields.asSequence().map {
            val vals= (it as SiField<T, Any?>).forceGet(this)
            Pair(it, vals)
        }
    }

@get:JvmName("fieldValuesTree")
val <T: Any> T.fieldValuesTree: NestedSequence<Pair<SiField<T, *>, Any?>>
    get(){
        return nestedSequence(this::class.si.classesTree){ innerCls: SiClass<*> ->
            innerCls.declaredFields //.iterator()//.asSequence()
                .map {
                    val vals= (it as SiField<T, Any?>).forceGet(this)
                    Pair(it, vals)
                }
                .iterator()
        }
    }

@get:JvmName("nestedFieldValuesTree")
val Any.nestedFieldValuesTree: NestedSequence<Pair<SiField<Any, *>, Any?>>
    get()= nestedSequenceSimple<Pair<SiField<Any, *>, Any?>>(fieldValuesTree){
        it.second?.fieldValuesTree?.iterator()
    }