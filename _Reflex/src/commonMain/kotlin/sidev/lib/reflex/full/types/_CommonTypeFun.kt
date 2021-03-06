package sidev.lib.reflex.full.types

//import sidev.lib.check.contentEquals
import sidev.lib.collection.sequence.flattenToNested
import sidev.lib.collection.sequence.withLevel
import sidev.lib.collection.toArrayOfNotNul
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.contentEquals
import sidev.lib.collection.intersect
import sidev.lib.collection.leveledIterator
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiType
import sidev.lib.reflex.SiTypeProjection
import sidev.lib.reflex.core.SiReflexConst
import sidev.lib.reflex.core.createType
import sidev.lib.reflex.full.classesTree
import sidev.lib.reflex.si


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun getCommonClass(vararg classes: SiClass<*>): SiClass<*> {
//    prine("getCommonClass() classes= $classes")
    if(classes.isEmpty())
        throw NoSuchElementException("""Tidak bisa mendapatkan common-class dari list "classes" kosong.""")
    val usedClasses= classes.asSequence().filter { it != SiReflexConst.nullClass }.toList() //Kelas Null tidak dihitung karena hanya sbg representasi pada operasi [getCommonType].
        // Pake SiReflexConst.nullClass agar lebih aman saat di Js.
    val superClassList= usedClasses.first().classesTree.withLevel().toMutableList().distinct()
        .sortedBy { it.level }.map { it.value } as MutableCollection<SiClass<*>>

    for(i in 1 until usedClasses.size)
        superClassList intersect usedClasses[i].classesTree.toMutableList()
    return if(superClassList.isNotEmpty()) superClassList.first()
    else Any::class.si
}
fun getCommonClass(vararg any: Any): SiClass<*> = getCommonClass(*any.toArrayOfNotNul { it::class.si })

fun getCommonType(vararg types: SiType): SiType {
//    prine("==types= ${types.string}")
    if(types.isEmpty())
        throw NoSuchElementException("""Tidak bisa mendapatkan common-type dari list "types" kosong.""")

    val isMarkedNullable= types.find { it.isMarkedNullable }?.isMarkedNullable ?: false

    val usedTypes= types.asSequence().filter { it != SiReflexConst.nullType }.toList()
    val classesArray= types.mapNotNull { it.classifier as? SiClass<*> }.toTypedArray()
    val commonClass= getCommonClass(*classesArray)

    val commonTypeArgs: MutableList<SiTypeProjection> = mutableListOf()
    if(commonClass.typeParameters.isNotEmpty()){ //Agar tidak terjadi komputasi mahal yg melibatkan [KTypeProjection.nestedProjectionsTree].
        val foundTypeArgs: MutableList<List<SiTypeProjection>> = mutableListOf()
        for(typeArg in usedTypes.map { it.nestedProjectionsTree }.asSequence().flattenToNested()){
//        prine("typeArg= $typeArg getCommonType() -> typeArg.type?.classifier= ${typeArg.type?.classifier} commonClass= $commonClass")
            if(typeArg.type?.classifier == commonClass){
                foundTypeArgs += typeArg.type!!.arguments
//            break
            }
        }

//    prine("==== foundTypeArgs= $foundTypeArgs")
        //Jika foundTypeArgs == types, maka typeArg disimpulkan menjadi supertype dari commonClass.
        // Contoh kasus ini adalah Int, String, dan Double di mana common-class adalah Comparable<T> di mana T merupakan cyclic type-param.
        // Jika scr program, foundTypeArgs == [Int, String, Double] di mana jika dipanggil fungsi [getCommonType] ini lagi,
        // maka akan terjadi infinite loop.
        if(foundTypeArgs.flatten().mapNotNull{ it.type }.contentEquals(usedTypes, checkOrder = false))
            commonTypeArgs += commonClass.supertypes[0].simpleTypeProjection
        else{
            for(typeArgs in foundTypeArgs.leveledIterator){
                if(typeArgs.isEmpty()) continue
                commonTypeArgs += getCommonType(*typeArgs.toArrayOfNotNul { it.type }).simpleTypeProjection
            }
        }
    }
    return commonClass.createType(commonTypeArgs, isMarkedNullable)
}
fun getCommonType(vararg any: Any?): SiType = getCommonType(*any.toArrayOfNotNul { it.inferredType.type })

