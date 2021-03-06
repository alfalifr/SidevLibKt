package sidev.lib.reflex

import sidev.lib.annotation.Rename
import sidev.lib.reflex.annotation.AnnotatedFunctionClass
//import sidev.lib.annotation.Rename
//import sidev.lib.collection.string
import sidev.lib.console.prin
import sidev.lib.reflex.annotation.AnnotatedFunctionClassManager

//import sidev.lib.reflex.type.inferElementType

class ClsGen<T, R>(val isi: T, var list: List<R>)

class AE<out E, S: AE<out E, S, F>, F: S> where S: CharSequence

annotation class Ano
annotation class Anotasi<T: Number, O>(val a: Int= 1) //: Ano()
/*
fun anot(){
    val aa: Annotation
    val a= A::class.annotations.first()
}
 */


@Anotasi<Int, String>
interface A
interface Y
interface X: Y{
    val x: String
}
open class B

class C: Z, B(){
    override val a: Boolean
        get() = true
}

data class AAD(val a: Int= 100, val b: Boolean, val bClss: B= B(), val dClss: D?)



interface Z: A{
    val a: Boolean
}
open class F{
    val f= 0
}
open class E{
    val e= true
    val fDiE= F()
}
open class D{
    var d= 1
    val dStr= ""
    val eDiD= E()
    val fDiD= F()
}

open class AA____
open class AA___: AA____()
open class AA__: AA___(){
    val aa__= 1
}
open class AA_: AA__(){
    var aa_= 123
}
sealed class AA: AA_(){
    private val aa= ""
    var dDariAA= D()
}
sealed class AB: AA(){
    protected val ab= "ok"
    private val ab_2= "ok"
    var ab_3= 6
//    @JvmField
    abstract var ab_abs: Int
    abstract fun ada()
}

annotation class FunAnot(val a: Int= 101)
open class BlaBla
interface BlaBlaInt
open class BlaBla2: BlaBla(), BlaBlaInt

@Anotasi<Int, Char>
open class AC<T>(val poinConstr: Poin): Z, Y, X, AB(), AnnotatedFunctionClass
        where T: BlaBla, T: BlaBlaInt{
    constructor(): this(Poin(1, 3))

    override val annotatedFunctionClassManager: AnnotatedFunctionClassManager? = AnnotatedFunctionClassManager(annotatedFunctionClassOwner)

    val ac= "ppop"
    private var acPriv= "aaa"
    var acStr1= "aaa1"
    var acStr2= "aaa2"
    override val a: Boolean = true
//    var listInt: List<Int?>? = listOf(1, 2, 4)
    override val x: String
        get() = "as"
    var poin= Poin(1, 2)

    var acStr3: String
        set(v){ acStr1= v }
        get()= acStr2

    var array= arrayOf(1, 2, 3)
    lateinit var poinLate: Poin //= Poin(1, 2)
//    @JvmField
    override var ab_abs: Int= 10

    val aLazy: CharSequence by lazy {
        "nilai lazy"
    }

    @Anotasi<Int, Any> @FunAnot(10)
    fun someFun(x: Int) = prin("\n\n ==== Halo dari AC.someFun() x= $x ==== \n\n")
    @Anotasi<Int, Any> @FunAnot(2)
    fun <T: BlaBlaInt, R: Int> someOtherFun(x: Any, az: Int = 1) where T: BlaBla = prin("\n\n ==== Halo dari AC.someOtherFun() x= $x az= $az ==== \n\n")
    override fun ada() {}
}

class Poin(var x: Int= 13, var y: Int, @property:Rename("az") var z: Int= 10){
    constructor(): this(12, 100)
    @Rename("aa_aa_diPoin") var aa_diPoin= AA_()
}

@Anotasi<Int, Char>
class Bool{
    val bool= true
}

open class AD_(val d: Int){
    constructor(): this(1)
    constructor(c: Int, d: Int): this(d)
}

class AD(val a: Int= 2, val b: Int= 15): AD_(a, 9){
    constructor(): this(1, 2)
    constructor(c: Int): this(c, 2)
    val aa: Int= 1
}

data class ParamTest(val a: Int, val b: String, val c: Int= 0){
    var ab= 10
    var z= 101
    var ac= "afa"
}

class Asal{
    var a: Int= 10
    var b= 0
}
class Tujuan{
    val a: Int= 1
    var b= "asad"
}

class ClsS{
    var isi= 1
    val b= 2
}


fun ada(z: ClsS){
    z.isi= 2
}

open class Food
open class FastFood: Food()
open class Burger: FastFood()

open class Producer<out T: Food>{
    open fun produce(): T? = null
}
open class Consumer<in T: Food>{
    open fun consume(ins: T) {}
}

open class FoodStore: Producer<Food>()
open class FastFoodStore: Producer<FastFood>()
open class BurgerStore: Producer<Burger>()

open class Everyone: Consumer<Food>()
open class ModernPipel: Consumer<FastFood>()
open class American: Consumer<Burger>()


enum class En(val a: Int, val pos: Int){
    A(2, 10),
    B(2, 3),
    C(3, 5);
    val b= 12
}


class NoConstr private constructor()

/*
@Suppress("UNREACHABLE_CODE")
fun main(args: Array<String>){
//    aadad()
    println("=================AC::class.memberProperties===========")
    for((i, member) in AC::class.memberProperties.withIndex()){
        println("i= $i member= $member")
        for((u, param) in member.parameters.withIndex()){
            println("--u= $u param= $param")
        }
    }
    println("=================AC::class.declaredMemberProperties===========")
    for((i, member) in AC::class.declaredMemberProperties.withIndex()){
        println("i= $i member= $member")
        for((u, param) in member.parameters.withIndex()){
            println("--u= $u param= $param")
        }
    }

    val regex2= "[^A-Za-z0-9 ]".toRegex().find(':'.toString())
    val regex= "aku mau makan dah\n".contains("[^A-Za-z0-9 ]".toRegex())
    prin("regex= $regex regex2= $regex2")

    prin("A::class.isAbstract = ${A::class.isAbstract}")
/*
    println("\n============= BATAS Exc ==============\n")
    val err = try{ "aad".toInt(); null }
    catch (e: Exception){ e }
    val exc= Exc(err?.message, err)
    throw exc
// */

    val arrInfer= arrayOf(1, 2, "str", 2.0)
    prin("inferElementType(arrInfer)= ${inferElementType(arrInfer)}")
/*
    println("\n============= BATAS AC Kotlin.classesTree ==============\n")
    for((i, member) in AC::class.si.members.withIndex()){
        prin("i= $i member= ${member}")
    }
 */

    prin("AC::class.si= ${AC::class.si}")
    println("\n============= BATAS AC siMembers ==============\n")
    for((i, member) in AC::class.si.members.withIndex()){
        prin("i= $i member= ${member}")
//        for((u, param) in member.parameters.withIndex())
//            prin("--u= $u param= ${param}")
    }

    println("\n============= BATAS AC siSonstr ==============\n")
    for((i, constr) in AC::class.si.constructors.withIndex())
        prin("i= $i constr= ${constr}")


    println("\n=============BATAS members ==============\n")
    for((i, prop) in AC::class.members.withIndex())
        println("i= $i prop name= $prop ") //val= ${try{ prop.forcedGet(acObj as AC)}}

//    AC::class.declaredMemberPropertiesTree.withLevel()
    println("\n============= BATAS AC Kotlin.si.classesTree ==============\n")
    for((i, leveledClass) in AC::class.si.classesTree.withLevel().withIndex()){
        prin("i= $i level= ${leveledClass.level} class= ${leveledClass.value}")
    }

    println("\n============= BATAS AC Kotlin.si.classesTree ==============\n")
    for((i, clazz) in AC::class.si.classesTree.withIndex()){
        prin("i= $i class= $clazz")
    }

    println("\n============= BATAS AC Kotlin.si.declaredMemberPropertiesTree ==============\n")
    for((i, leveledProp) in AC::class.si.declaredMemberPropertiesTree.withLevel().withIndex()){
        prin("i= $i level= ${leveledProp.level} prop= ${leveledProp.value}")
    }

    prine("Void::class.constructors.size= ${Void::class.constructors.size}")
    println("\n============= BATAS AC Java.getterMethodsTree ==============\n")
    for((i, method) in AC::class.java.getterMethodsTree.withIndex()){
        prin("i= $i method= $method return= ${method.returnType}")
    }

    class AB: ArrayList<Int>()
    val superclass= AB::class.java.superclass
    val genSuper= AB::class.java.genericSuperclass?.clazz
    prin("superclass= $superclass genSuper= $genSuper")

    println("\n============= BATAS AC Java.setterMethodsTree ==============\n")
    for((i, method) in AC::class.java.setterMethodsTree.withIndex()){
        prin("i= $i method= $method return= ${method.returnType}")
    }

    println("\n============= BATAS AC Java.fieldsTree ==============\n")
    for((i, prop) in AC::class.java.fieldsTree.withIndex()){
        prin("i= $i prop= $prop")
    }
    println("\n============= BATAS AC Java.methodsTree ==============\n")
    for((i, method) in AC::class.java.methodsTree.withIndex()){
        prin("i= $i method= $method return= ${method.returnType} void= ${method.returnType == Void::class.java}")
    }

    println("\n============= BATAS List plus ==============\n")
    val list= ArrayList<Int>()
    list + 1 + 1 + 3 - 1 -3
    for((i, e) in list.withIndex()){
        prin("i= $i e= $e")
    }


    println("\n============= BATAS memberProperties ==============\n")
    val asal= Asal()
    val tujuan= Tujuan()
    copySimilarProperty(asal, tujuan)
    prin("tujuan.a= ${tujuan.a} tujuan.b= ${tujuan.b}")
    prin("A::class.constructors.size = ${A::class.constructors.size}")
    prin("NoConstr::class.constructors.size = ${NoConstr::class.constructors.size}")

    println("\n============= BATAS memberProperties ==============\n")
    val ac= AC<BlaBla2>(Poin(y=10))
    for((i, prop) in ac::class.memberProperties.withIndex()){
        prin("i= $i prop= $prop")
    }
/*
    println("\n============= BATAS implementedAccesiblePropertiesValueMapTree ==============\n")
    for((i, prop) in ac.implementedAccesiblePropertiesValueMapTree.withIndex()){
        prin("i= $i prop= $prop")
    }
 */

    println("\n============= BATAS implementedPropertiesValueMapTree ==============\n")
    for((i, prop) in ac.implementedPropertyValuesTree.withIndex()){
        prin("i= $i prop= $prop")
    }
///*
    println("\n============= BATAS implementedNestedPropertyValuesTree ==============\n")
    for((i, prop) in ac.implementedNestedPropertyValuesTree.withIndex()){
        prin("i= $i prop= $prop")
    }
// */

    println("\n============= BATAS Anotasi ==============\n")
    for((i, anotasi) in Anotasi::class.typeParameters.withIndex()){
        prin("i= $i anotasi= $anotasi")
        for((u, bound) in anotasi.upperBounds.withIndex()){
            prin("--u= $u anotasi= $bound")
        }
    }

    val poin= Poin(y= 11)
//    ac.callAnnotatedFunctionWithParamContainer(FunAnot::class, poin){ it.a == 2 }
//    val aaaaa= Anotasi<Int, String>(1)

    val ano1= AC::class.findAnnotation<Anotasi<Int, *>>()
    val ano2= Bool::class.findAnnotation<Anotasi<Int, Char>>()
    val ano3= AC::class.findAnnotation<Anotasi<Int, Boolean>>()

    prin("ano1 == ano2 => ${ano1 == ano2} ano2 == ano3 => ${ano2 == ano3} ano3 == ano1 => ${ano3 == ano1}")

//    Class.forName("").kotlin.java.isInterface
    AD(a= 0)
    println("\n=============BATAS constructors ==============\n")
    for(constr in AD::class.constructors){
        prin("constr= ${constr}")
    }
/*
    println("\n=============BATAS contrustorPropertiesTree ==============\n")
    for(prop in AD::class.contrustorPropertiesTree){
        prin("prop= ${prop}")
    }
 * /

    val constrTree= AD::class.contructorsTree.iterator()
    for(i in 0 until 20)
        constrTree.hasNext()
    println("\n=============BATAS contructorsTree ==============\n")
    for((i, constr) in constrTree.withIndex()){
        prin("i= $i prop= $constr")
    }

    println("\n=============BATAS contructorParamsTree ==============\n")
    for((i, param) in AD::class.contructorParamsTree.withIndex())
        prin("i= $i prop= $param")

    println("\n=============BATAS contructorPropertiesTree ==============\n")
    for((i, prop) in AD::class.contructorPropertiesTree.withIndex())
        prin("i= $i prop= $prop")

    println("\n=============BATAS classesTree ==============\n")
    for((i, prop) in AD::class.classesTree.withIndex())
        prin("i= $i prop= $prop")

 */


/*
    val classX= X::class
    val classX2= X::class

    prind("classX= $classX classX == classX2 => ${classX == classX2}")

    for(supert in AC::class.supertypes){
        (supert.classifier as KClass<*>)
        println("super= $supert supert is KClass<*> = ${supert.classifier is KClass<*>} isInterface= ${supert.isInterface}")
    }
 */
/*
    println("\n=============BATAS #1==============\n")

    val supertypeSeq= AC::class.supertypesJvm(true)

    for((i, supert) in supertypeSeq.withIndex()){
        println("i= $i super= $supert isInterface= ${supert.isInterface}")
    }

    println("\n=============BATAS #2==============\n")

    for((i, supert) in supertypeSeq.withIndex()){
        println("i= $i super= $supert isInterface= ${supert.isInterface}")
    }

    println("\n=============BATAS superInterfacesTree ==============\n")

    for((i, supert) in AC::class.java.superInterfacesTree.withIndex()){
        println("i= $i super= $supert isInterface= ${supert.isInterface}")
    }

    println("\n=============BATAS==============\n")

    for((i, supert) in AC::class.java.superclassesTree.withIndex()) {
        println("i= $i super= $supert isInterface= ${supert.isInterface}")
    }

    println("\n=============BATAS sealedSubClass for biasa ==============\n")

    for((i, subclass) in AA::class.sealedSubclasses.withIndex()){
        println("i= $i subclass= $subclass isSealed= ${subclass.isSealed}")
    }

    println("\n=============BATAS sealedSubclassesTree==============\n")

    for((i, subclass) in AA::class.sealedSubclassesTree.withIndex()){
        println("i= $i subclass= $subclass isSealed= ${subclass.isSealed}")
    }


    println("\n============= AC::class.getSealedClassName()= ${AC::class.getSealedClassName()} ==============\n")

    println("\n=============BATAS==============\n")

    println("default int= ${defaultPrimitiveValue<Int>()}")
    println("default val == null => ${defaultPrimitiveValue<Array<Int>>() == null}")
    println("Array<String>::class.qualifiedName= ${Array<Int>::class.qualifiedName}")
    println("Array<Int>::class == Array<String>::class => ${Array<Int>::class == Array<String>::class}")
    val acObj= AC()
    println("AAD data= ${new<AAD>()}")
*/
    println("\n=============BATAS nestedDeclaredMemberPropertiesTree ==============\n")
    for((i, prop) in AC::class.si.nestedDeclaredMemberPropertiesTree.withIndex())
        println("i= $i prop name= $prop ") //val= ${try{ prop.forcedGet(acObj as AC)}}

/*
    println("\n=============BATAS propertiesTree_simple ==============\n")
    for((i, prop) in AC::class.propertiesTree_.withIndex())
        println("i= $i prop name= ${prop.name} ") //val= ${try{ prop.forcedGet(acObj as AC)}}
// * /
    println("\n=============BATAS declaredPropertiesTree ==============\n")
    for((i, prop) in AC::class.declaredPropertiesTree.withIndex())
        println("i= $i prop name= ${prop.name} ") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS nestedDeclaredPropertiesTree ==============\n")
    for((i, prop) in AC::class.nestedDeclaredPropertiesTree.withIndex())
        println("i= $i prop name= ${prop.name} prop= $prop") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS implementedPropertiesTree ==============\n")
    for((i, prop) in AC::class.implementedPropertiesTree.withIndex())
        println("i= $i prop name= ${prop.name} prop= $prop") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS nestedImplementedPropertiesTree ==============\n")
    for((i, prop) in AC::class.nestedImplementedPropertiesTree.withIndex())
        println("i= $i prop name= ${prop.name} prop= $prop") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS declaredMemberProperties ==============\n")
    for((i, prop) in AC(Poin(1,2))::class.declaredMemberProperties.withIndex())
        println("i= $i prop name= ${prop.name} ") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS Z declaredMemberProperties ==============\n")
    for((i, prop) in Z::class.declaredMemberProperties.withIndex())
        println("i= $i prop name= ${prop.name} ") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS superclasses ==============\n")
    for((i, superclass) in AC::class.superclasses.withIndex())
        println("i= $i superclass name= ${superclass.qualifiedName} ") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS classesTree ==============\n")
    for((i, clazz) in AC::class.classesTree.withIndex())
        println("i= $i class name= ${clazz.qualifiedName} ") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS implementedPropertiesValueMap ==============\n")
    for((i, pairOfProp) in AC(Poin(1,2)).implementedPropertiesValueMap.withIndex())
        println("i= $i prop= ${pairOfProp.first} value= ${pairOfProp.second}") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS nestedImplementedPropertiesValueMap ==============\n")
    for((i, pairOfProp) in AC(Poin(1,2)).nestedImplementedPropertiesValueMap.withIndex())
        println("i= $i prop= ${pairOfProp.first} value= ${pairOfProp.second}") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS implementedPropertiesValueMapTree ==============\n")
    for((i, pairOfProp) in AC(Poin(1,2)).implementedPropertiesValueMapTree.withIndex())
        println("i= $i prop= ${pairOfProp.first} value= ${pairOfProp.second}") //val= ${try{ prop.forcedGet(acObj as AC)}}

    println("\n=============BATAS nestedImplementedPropertiesValueMapTree ==============\n")
    for((i, pairOfProp) in AC(Poin(1,2)).nestedImplementedPropertiesValueMapTree.withIndex())
        println("i= $i prop= ${pairOfProp.first} value= ${pairOfProp.second}") //val= ${try{ prop.forcedGet(acObj as AC)}}
// */
    val oldAc= object: AC<BlaBla2>(Poin(2,3)){

    }
    prin("oldAc::class.isAllMembersImplemented = ${oldAc::class.si.isAllMembersImplemented}")
    prin("oldAc::class.isShallowAnonymous = ${oldAc::class.si.isShallowAnonymous}")
    prin("oldAc::class.si.qualifiedName= ${oldAc::class.si.qualifiedName}")
    prin("oldAc::class.si.superclass= ${oldAc::class.si.superclass}")
/*
    for((i, abst) in (oldAc::class.si.declaredMembersTree - oldAc::class.implementedMembersTree).withIndex()){
        prin("i= $i abst= $abst")
    }
 */
    val oldAcSim= AC<BlaBla2>(Poin(y=12))

//    var newAc= //oldAc.clone(true)
    oldAc.poin.x= 100

    prine("oldAc.array[1]= ${oldAc.array[1]}")
    prine("Int::class.isPrimitive= ${Int::class.si.isPrimitive}")
    prine("Int::class.leastRequiredParamConstructor= ${Int::class.si.leastRequiredParamConstructor}")
    prine("IntArray::class= ${IntArray::class}")

    val arr= arrayOf(0, 1, 3)
    val arr2= arr.clone()
    val intArr= IntArray(2)

    prin("arr::class.typeParameters.first().upperBounds.first() = ${arr::class.typeParameters.first().upperBounds.first()} arr::class.typeParameters.size= ${arr::class.typeParameters.size}")

    arr2[2]= 1

    prin("arr[2]= ${arr[2]} arr2[2]= ${arr2[2]} arr::class= ${arr::class} arr2::class= ${arr2::class}")

    println("\n=============BATAS Int.constructor ==============\n")
    for((i, constr) in Int::class.constructors.withIndex()){
        prin("i= $i constr= $constr")
    }

    val newAc= oldAc.clone<AC<BlaBla2>>(true)
/*
    { clazz, param ->
//        prine("Poin clazz= $clazz simpleName= ${clazz.simpleName} param= $param nmae= ${param.name} kind= ${param.kind}")
        when(clazz){
            AC::class -> Poin(31, 41)
            Poin::class -> {
//                param.kind
//                prine("Poin param= $param nmae= ${param.name} kind= ${param.kind}")
                when(param.name){
//                    "x" -> 451
                    "y" -> 651
                    else -> null
                }
            }
            else -> null
        }
    }
// */
    oldAc.poin.x= 70
    oldAc.poin.y= 7
    oldAc.poin.aa_diPoin.aa_= 17
    oldAc.aa_= 43
    oldAc.poinLate= Poin(33, 73)
    oldAc.poinConstr.x= 12
    oldAc.array[1]= 110
/*
    println("\n=============BATAS memberFunctions ==============\n")
    for((i, func) in oldAc::class.memberFunctions.withIndex()){
        println("i= $i func= $func")
        for((u, param) in func.parameters.withIndex()){
            println("__ u= $u param= $param name= ${param.name} kind= ${param.kind} className= ${param::class.simpleName}")
        }
    }

    println("\n=============BATAS javConstrs ==============\n")
    val javConstrs= oldAc::class.java.constructors
    for((i, constr) in javConstrs.withIndex()){
        prin("i= $i, constr= $constr")
    }
 */

/*
    oldAc.aa_
    newAc.aa_
    oldAc.poin.aa_diPoin.aa_
    newAc.poin.aa_diPoin.aa_
    oldAc.poin.x
    oldAc.poin.y
    newAc.poin.x
    newAc.poin.y
 */

    println("\n=============BATAS clone() ==============\n")
    println("oldAc.poin.x= ${oldAc.poin.x} oldAc.poin.y= ${oldAc.poin.y} newAc.poin.x= ${newAc.poin.x} newAc.poin.y= ${newAc.poin.y}")
    println("oldAc.poin.aa_diPoin.aa_= ${oldAc.poin.aa_diPoin.aa_} newAc.poin.aa_diPoin.aa_= ${newAc.poin.aa_diPoin.aa_}")
    println("oldAc.aa_= ${oldAc.aa_} newAc.aa_= ${newAc.aa_}")
    println("oldAc.poinConstr.x= ${oldAc.poinConstr.x} oldAc.poinConstr.y= ${oldAc.poinConstr.y} newAc.poinConstr.x= ${newAc.poinConstr.x} newAc.poinConstr.y= ${newAc.poinConstr.y}")
    println("oldAc.poinLate.x= ${oldAc.poinLate.x} oldAc.poinLate.y= ${oldAc.poinLate.y} newAc.poinLate?.x= ${newAc.getLateinit { poinLate.x }} newAc.poinLate?.y= ${newAc.getLateinit { poinLate.y }}")
    prin("oldAc.array= ${oldAc.array.string} newAc.array= ${newAc.array.string}")


    println("\n============= BATAS sealedSubclasses ==============\n")
    for((i, sub) in AA::class.si.sealedSubclassesTree.withIndex()){
        println("i= $i sub sealed= $sub")
    }
// * /

    println("\n============= BATAS CachedSequence ==============\n")
    val strSeq= sequenceOf("Aku", "Mau", "Makan")
    val strSeq2= sequenceOf("Kamu" , "Dan", "Dia")
    val lazySeq= CachedSequence<String>()
    lazySeq += "Halo"
    lazySeq += "Bro"
    lazySeq + strSeq + strSeq2
///*
    val containsAku= "Aku" in lazySeq //.contains("Aku")
    val containsKamu= "Kamu" in lazySeq //.contains("Kamu")
    val containsDiaJelek= "Dia Jelek" in lazySeq //.contains("Kamu")
    val indexMau= lazySeq.indexOf("Mau")
    val indexKamu= lazySeq.indexOf("Kamu")
    val ke4= lazySeq[4]
    prin("indexMau= $indexMau ke4= $ke4 containsAku= $containsAku containsKamu= $containsKamu containsDiaJelek= $containsDiaJelek indexKamu= $indexKamu")
// * /

    println("\n============= BATAS CachedSequence.iterator() ==============\n")
    for((i, data) in lazySeq.withIndex()){
        prin("i= $i data= $data")
    }

    println("\n============= BATAS LazyHashMap ==============\n")

    val pairSeq= sequenceOf("Aku" to 3, "Dia" to 10, "Kamu" to 1)
    val pairSeq2= sequenceOf("Makan" to 2, "Belajar" to 5, "Tidur" to 0)
    val lazyMap= LazyHashMap<String, Int>()
    lazyMap["Mau"]= 7
    lazyMap["Iya"]= 6

    lazyMap + pairSeq + pairSeq2

    println("\n============= BATAS LazyHashMap.iterator() ==============\n")
    for((i, data) in lazyMap.withIndex()){
        prin("i= $i data= $data")
    }
/*
    val valueMau= lazyMap["Mau"]
    val valueIya= lazyMap["Iya"]
    val valueKamu= lazyMap["Kamu"]
    val valueTidur= lazyMap["Tidur"]
    val contains5= lazyMap.containsValue(5)
    val contains0= lazyMap.containsValue(0)
    val contains4= lazyMap.containsValue(4)

    prin("valueMau= $valueMau valueIya= $valueIya valueKamu= $valueKamu contains5= $contains5 contains0= $contains0 contains4= $contains4 valueTidur= $valueTidur")
 */


    println("\n============= BATAS Enum.ordinalNamePairs ==============\n")
    enumValues<En>()
    for(pair in ordinalNamePairs<En>()){
        prin("pair ordinal= ${pair.first} name= ${pair.second}")
    }

    println("\n============= BATAS Enum.toArray ==============\n")
    enumValues<En>()
    for(name in En.A.toArray { it.name }){
        prin("pair name= $name")
    }

    println("\n============= BATAS Enum.data ==============\n")
    for((i, data) in En.A.data.withIndex()){
        prin("i= $i pair data= $data")
    }

    println("\n============= BATAS Enum.implementedPropertyValuesTree ==============\n")
    for((i, data) in En.A.implementedPropertyValuesTree.withIndex()){
        prin("i= $i pair data= $data")
    }
    prin("En.A.b= ${En.A.b} En.B.b= ${En.B.b}")
/*
    println("\n============= BATAS Enum.contrustorsTree ==============\n")
    enumValues<En>()
    for(data in En.A::class.contructorsTree){
        prin("pair data= $data")
    }
*/

    println("\n============= BATAS Enum.declaredMemberProperties ==============\n")
    for(prop in En::class.declaredMemberProperties)
        println("Enum prop= $prop")

    println("\n============= BATAS Seq.minus ==============\n")
    val seq1= sequenceOf("aku", "makan", "ayam", "elo")
    val seq2= sequenceOf("kamu", "makan", "ayam", "halo")

    for(data in seq1 - seq2){
        prin("data= $data")
    }
//    En.to

// */

// */
/*
    val transac= dum_transaction.last()
    println("\n============= BATAS Transac.nestedImplementedPropertiesValueMapTree ==============\n")
    for((i, valMap) in transac.nestedImplementedPropertiesValueMapTree.withIndex())
        println("Transac i= $i prop= ${valMap.first} value= ${valMap.second}")

    println("\n============= BATAS Transac.declaredMemberProperties CLONE ==============\n")
    val transacClone= transac.clone()
    for((i, valMap) in transacClone.implementedPropertiesValueMapTree.withIndex())
        println("Transac i= $i prop= ${valMap.first} value= ${valMap.second}")

    prin("\n\n transacClone.commodity?.name= ${transacClone.commodity?.name}")
 */
/*
    val oldAc= object: AC(Poin(1, 2)){
        override val x: String
            get() = "adad"
    }
    val constrSize= oldAc::class.constructors.size
    prine("constrSize= $constrSize")
//    oldAc::class.createInstance()
    val newAc1= oldAc.clone()
    val newAc2= oldAc.clone(false)

    oldAc == newAc1
    oldAc == newAc2
    prin("oldAc == newAc1 = ${oldAc == newAc1} oldAc == newAc2= ${oldAc == newAc2}")
 * /

    println("\n============= BATAS implementedPropertiesValueMap ==============\n")
    val array= Array(2){ AC(Poin(y=10)) }
    val intArray= IntArray(2)
    val booleanArray= BooleanArray(2)
    val newIntArray= intArray.arrayClone()// as IntArray

    newIntArray[0]= 10

    prin("intArray.size => ${intArray.size} newIntArray.size => ${newIntArray.size} intArray == newIntArray => ${intArray == newIntArray} intArray[0]= ${intArray[0]} newIntArray[0]= ${newIntArray[0]}")
//    val a= array::size
    val newArray= array.deepClone()
//    array.copyOf()
//    System.arraycopy(array, 0, newArray, 0, 2)

    val oldArrAc= array[0]
    oldArrAc.poin.x= 11111
    val newArrAc= newArray[0]

    val isArray= "adad"::class.isArray

    prin("isArray= $isArray")
    prin("Array<Int>::class == Array<String>::class => ${Array<Int>::class == Array<String>::class} Array<Int>::class= ${Array<Int>::class} oldArrAc == newArrAc => ${oldArrAc == newArrAc} array == newArray => ${array == newArray}")
    prin("oldArrAc.poin.x= ${oldArrAc.poin.x} newArrAc.poin.x= ${newArrAc.poin.x}")
    for(prop in array.implementedPropertiesValueMap)
        println("Array prop= $prop")


}
 */


/*
enum class En(val a: Int, val pos: Int){
    A(2, 10),
    B(2, 3),
    C(3, 5);
    val b= 12
}
 */

suspend fun aga(): Int{
    return 1
}

fun asa(){
}



/*
fun <T> Sequence<T>.cut(other: Sequence<T>): Sequence<T>{
    return object: Sequence<T>{
        override fun iterator(): Iterator<T>
            = object : SkippableIteratorImpl<T>(other.iterator()){

            override fun skip(now: T): Boolean {
                other.toList()
            }
        }
    }
}

// */