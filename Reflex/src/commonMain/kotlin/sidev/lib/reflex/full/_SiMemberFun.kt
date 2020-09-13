package sidev.lib.reflex.full

import sidev.lib.check.asNotNullTo
import sidev.lib.collection.iterator.skip
import sidev.lib.collection.sequence.NestedSequence
import sidev.lib.collection.sequence.nestedSequence
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiFunction
import sidev.lib.reflex.SiProperty1
import kotlin.jvm.JvmName


@get:JvmName("declaredMemberFunctions")
val SiClass<*>.declaredMemberFunctions: Sequence<SiFunction<*>>
    get()= members.asSequence().filter { it is SiFunction<*> } as Sequence<SiFunction<*>>

@get:JvmName("declaredMemberProperties")
val <T: Any> SiClass<T>.declaredMemberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>


@get:JvmName("declaredMemberPropertiesTree")
val <T: Any> SiClass<T>.declaredMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= nestedSequence(classesTree){ cls: SiClass<*> -> cls.declaredMemberProperties.iterator() }
            as NestedSequence<SiProperty1<T, *>>

@get:JvmName("declaredMemberFunctionsTree")
val SiClass<*>.declaredMemberFunctionsTree: NestedSequence<SiFunction<*>>
    get()= nestedSequence(classesTree){ cls: SiClass<*> -> cls.declaredMemberFunctions.iterator() }

@get:JvmName("nestedDeclaredMemberPropertiesTree")
val SiClass<*>.nestedDeclaredMemberPropertiesTree: NestedSequence<SiProperty1<Any, *>>
    get()= nestedSequence<SiClass<*>, SiProperty1<Any, *>>(classesTree, {
        it.returnType.classifier.asNotNullTo { cls: SiClass<*> -> cls.classesTree.iterator() }
    })
    { cls: SiClass<*> -> cls.declaredMemberProperties.iterator() as Iterator<SiProperty1<Any, *>> } //as NestedSequence<SiProperty1<T, *>>

@get:JvmName("implementedMemberPropertiesTree")
val <T: Any> SiClass<T>.implementedMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= declaredMemberPropertiesTree.skip { it.isAbstract }

@get:JvmName("implementedNestedMemberPropertiesTree")
val SiClass<*>.implementedNestedMemberPropertiesTree: NestedSequence<SiProperty1<Any, *>>
    get()= nestedDeclaredMemberPropertiesTree.skip { it.isAbstract }