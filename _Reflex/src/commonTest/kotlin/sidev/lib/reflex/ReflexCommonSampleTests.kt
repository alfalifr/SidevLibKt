package sidev.lib.reflex

import com.sigudang.android._Dummy.inboundList_created
import com.sigudang.android._Dummy.productList_full
import sidev.lib._config_.SidevLibConfig
import sidev.lib.annotation.SiRename
import sidev.lib.annotation.renamedName
import sidev.lib.collection.sequence.withLevel
import sidev.lib.console.log
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.console.prinp
import sidev.lib.number.*
import sidev.lib.environment.Platform
import sidev.lib.environment.platform
import sidev.lib.reflex.annotation.callAnnotatedFunction
import sidev.lib.reflex.core.createType
import sidev.lib.reflex.full.*
import sidev.lib.reflex.full.types.*
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class ReflexCommonSampleTests {
    @Test
    fun testMe() {
        prinp("\n==== testMe() Mulai ==== \n")
        assertTrue(Sample().checkMe() > 0)
    }

    @ExperimentalTime
    @Test
    fun reflexTest(){
        prinp("\n==== reflexTest() Mulai ==== \n")
        val siCls: SiClass<AC<*>>
        var timeTaken = measureTime { siCls = AC::class.si }
        prin("siCls= $siCls")
        prine("timeTaken = $timeTaken")

        prin("siCls.constructors= ${siCls.constructors}")
        prin("siCls.constructors.isEmpty()= ${siCls.constructors.isEmpty()}")

        timeTaken = measureTime {
            prin("\n============ AC::class.si.members ================\n")
            for((i, member) in AC::class.si.members.withIndex()){
                prin("i= $i member= $member host= ${member.descriptor.host} native= ${member.descriptor.native}")
                for((u, param) in member.parameters.withIndex())
                    prin("--u= $u param= $param default= ${param.defaultValue} native= ${param.descriptor.native}")
            }
        }
        prine("timeTaken = $timeTaken")
    }

    @Test
    fun reflexTypeTest(){
        prinp("\n==== reflexTypeTest() Mulai ==== \n")
        val singletonList= listOf("bla")
        val array5= arrayOf("bli")
        prin("singletonList::class.si.isCollection= ${singletonList::class.si.isCollection}")
        prin("array5::class.si.isCollection= ${array5::class.si.isCollection}")

        val array1= arrayOf(1,3,4,5,6)
        val intArray1= intArrayOf(1,3,4,5,6)
        val array2= arrayOf(1,3,4,5,6, 's', "ada")
        val array3= arrayOf(1,3,4,5,6, 2.3, 4f, 2L)
        val array4= arrayOf(1,3,4,5,6, 's', "ada", null)

        val commonClass1= getCommonClass(*array1)
        val commonClass2= getCommonClass(*array2)
        val commonClass3= getCommonClass(*array3)

        prin("commonClass1= $commonClass1")
        prin("commonClass2= $commonClass2")
        prin("commonClass3= $commonClass3")

        val inferreType1= array1.inferredType
        val inferreType2= array2.inferredType
        val inferreType3= array3.inferredType
        val inferreType4= array4.inferredType

        val is2SubtypeOf4= inferreType2.isSubTypeOf(inferreType4)

        prin("inferreType1= $inferreType1")
        prin("inferreType2= $inferreType2")
        prin("inferreType3= $inferreType3")
        prin("inferreType4= $inferreType4")
        prin("is2SubtypeOf4= $is2SubtypeOf4")
///*
        val clsGen= ClsGen(10.9.asNumber(), listOf(arrayOf("sf")))
        val clsGen2= clsGen.clone()
        clsGen.list= listOf(arrayOf("pa"))
        val clsInferredType= clsGen.inferredType

        prin("clsGen= $clsGen hash= ${clsGen.hashCode()} list= ${clsGen.list} clsInferredType= $clsInferredType")
        prin("clsGen2= $clsGen2 hash= ${clsGen2.hashCode()} list= ${clsGen2.list}")
// */
/*
        val listSingle= java.util.Collections.singletonList(9)
        prin("\n=============== listSingle.implementedPropertyValuesTree ===============\n")
        for((i, prop) in listSingle.implementedPropertyValuesTree.withIndex()){
            prin("i= $i prop= $prop returnType.class= ${prop.first.returnType.classifier}")
        }
 */

        prin("\n=============== Number::class.si.classesTree ===============\n")
        for((i, cls) in Number::class.si.classesTree.withIndex()){
            prin("i= $i cls= $cls")
        }
    }

    @Test
    fun constrTest(){
        prinp("\n==== constrTest() Mulai ==== \n")
        prin(AC::class.si.primaryConstructor)
    }

    @ExperimentalTime
    @Test
    fun cloneTest(){
        prinp("\n==== cloneTest() Mulai ==== \n")
        val ac= AC<BlaBla2>(Poin(y = 199))
        val ac2: AC<BlaBla2>
        var timeTaken = measureTime { ac2 = ac.clone() }
        prine("timeTaken= $timeTaken")

        assertEquals(ac.acStr3, ac2.acStr3)
        assertEquals(ac.acStr1, ac2.acStr1)
        assertEquals(ac.acStr2, ac2.acStr2)
        assertEquals(ac.poin.x, ac2.poin.x)
        assertEquals(ac.ab_abs, ac2.ab_abs)
        assertEquals(ac.dDariAA.d, ac2.dDariAA.d)

        val acPoinX_before= ac.poin.x
        val acDDariAAD_before= ac.dDariAA.d
        val acAcStr1_before= ac.acStr1
        val ac2AcStr2_before= ac2.acStr2
//        js("class;a")

        prin("\n============= Clone =============\n")
        prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
        prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
        prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
        prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
        prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
        prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")
        ac.poin.x= 12
        ac.dDariAA.d= 19
        ac.acStr1= "bbb1"
        ac2.acStr2= "bbb2"

        assertEquals(ac2.poin.x, acPoinX_before)
        assertEquals(ac2.dDariAA.d, acDDariAAD_before)
        assertEquals(ac2.acStr1, acAcStr1_before)
        assertEquals(ac.acStr2, ac2AcStr2_before)

        assertNotEquals(ac.poin.x, ac2.poin.x)
        assertNotEquals(ac.dDariAA.d, ac2.dDariAA.d)
        assertNotEquals(ac.acStr1, ac2.acStr1)
        assertNotEquals(ac.acStr2, ac2.acStr2)

        prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
        prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
        prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
        prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
        prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
        prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")
        prin("\n============= ac.implementedPropertyValuesTree =============\n")

        for((i, prop) in ac.implementedPropertyValuesTree.withIndex()){
/*
            if(prop.first.name == "aLazy"){
                prine("prop.first.name == \"aLazy\"")
                val lazyDelName= jsName(prop.second!!)
                prin("lazyDelName= $lazyDelName")
                log(prop.second!!)
                log(prop.second.asDynamic().initializer_0.toString())
            }
 */
            println("i= $i prop= $prop")
        }

        val ac3= AC<BlaBla2>()
        val ac4= AC<BlaBla2>()
        val list1= listOf(ac3, ac4)
        val list2= list1.clone()


        prin("list2::class.si= ${list2::class.si}")
        prin("list2::class.si.isCopySafe= ${list2::class.si.isCopySafe}")
        prin("list2::class.si.isCollection= ${list2::class.si.isCollection}")
        prin("\"afsa\"::class.si.isCopySafe= ${"afsa"::class.si.isCopySafe}")

        prin("\n============= List Clone ===============\n")
        list1[0].poin.x= 16
        list1[1].poin.y= 32
        prin("list1[0].poin.x= ${list1[0].poin.x} list1[1].poin.x= ${list1[1].poin.x}")
        prin("list1[0].poin.y= ${list1[0].poin.y} list1[1].poin.y= ${list1[1].poin.y}")
    }

    @ExperimentalTime
    @Test
    fun nativeCloneTest(){
        prinp("\n==== nativeCloneTest() Mulai ==== \n")
        val ac= AC<BlaBla2>(Poin(y = 199))
        val ac2: AC<BlaBla2>
        var timeTaken = measureTime { ac2 = ac.nativeClone() }
        prine("timeTaken= $timeTaken")
//        js("class;a")

        assertEquals(ac.acStr3, ac2.acStr3)
        assertEquals(ac.acStr1, ac2.acStr1)
        assertEquals(ac.acStr2, ac2.acStr2)
        assertEquals(ac.poin.x, ac2.poin.x)
        assertEquals(ac.ab_abs, ac2.ab_abs)
        assertEquals(ac.dDariAA.d, ac2.dDariAA.d)

        val acPoinX_before= ac.poin.x
        val acDDariAAD_before= ac.dDariAA.d
        val acAcStr1_before= ac.acStr1
        val ac2AcStr2_before= ac2.acStr2

        prin("\n============= Clone =============\n")
        prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
        prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
        prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
        prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
        prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
        prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")
        ac.poin.x= 12
        ac.dDariAA.d= 19
        ac.acStr1= "bbb1"
        ac2.acStr2= "bbb2"
        assertEquals(ac2.poin.x, acPoinX_before)
        assertEquals(ac2.dDariAA.d, acDDariAAD_before)
        assertEquals(ac2.acStr1, acAcStr1_before)
        assertEquals(ac.acStr2, ac2AcStr2_before)
        prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
        prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
        prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
        prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
        prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
        prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")
        prin("\n============= ac.implementedPropertyValuesTree =============\n")

        for((i, prop) in ac.implementedPropertyValuesTree.withIndex()){
/*
            if(prop.first.name == "aLazy"){
                prine("prop.first.name == \"aLazy\"")
                val lazyDelName= jsName(prop.second!!)
                prin("lazyDelName= $lazyDelName")
                log(prop.second!!)
                log(prop.second.asDynamic().initializer_0.toString())
            }
 */
            println("i= $i prop= $prop")
        }

        val ac3= AC<BlaBla2>()
        val ac4= AC<BlaBla2>()
        val list1= listOf(ac3, ac4)
        val list2= list1.nativeClone()

        prin("list2::class= ${list2::class}")
        prin("list2::class.isCopySafe= ${list2::class.isCopySafe}")
        prin("\"afsa\"::class.isCopySafe= ${"afsa"::class.si.isCopySafe}")
        prin("list2::class.isCollection= ${list2::class.isCollection}")
/*
        prin("\n============= list2::class.supertypes ===============\n")
        for((i, cls) in list2::class.supertypes.filter { it.classifier is KClass<*> }.withIndex())
            prin("i= $i cls= $cls")
 */

        prin("\n============= list2::class.classesTree ===============\n")
        for((i, cls) in list2::class.classesTree.withLevel().withIndex())
            prin("i= $i cls= $cls")
///*
        prin("\n============= ac3.implementedNestedPropertyValuesTree ===============\n")
        for((i, field) in ac3.implementedNestedPropertyValuesTree.withLevel().withIndex())
            prin("i= $i field= $field")
// */

        prin("\n============= List Clone ===============\n")
        list1[0].poin.x= 16
        list1[1].poin.y= 32
        prin("list1[0].poin.x= ${list1[0].poin.x} list1[1].poin.x= ${list1[1].poin.x}")
        prin("list1[0].poin.y= ${list1[0].poin.y} list1[1].poin.y= ${list1[1].poin.y}")
    }

    @Test
    fun nestedPropOrderTest(){
        class A(val x: Int= 1, val y: Int= 0)
        class B(val a: A= A())
        class C(val b: B= B())
        class D(val c: C= C())
        class E(val d: D= D())
        class F(val e: E= E(), val xyx: Int= 100)

        var currLevel= 0
        prin("\n============= F().implementedNestedPropertyValuesTree ===============\n")
        for((i, field) in F().implementedNestedPropertyValuesTree.withLevel().withIndex())
            prin("i= $i field= $field")
    }

    @Test
    fun propTest(){
        val ac= AC<BlaBla2>()
        val seq= ac.implementedPropertyValuesTree

        prin("\n============= ac.implementedNestedPropertyValuesTree ===============\n")
        for((i, field) in seq.withIndex())
            prin("i= $i field= $field")

        prin("\n============= ac.implementedNestedPropertyValuesTree ke-2 ===============\n")
        for((i, field) in seq.withIndex())
            prin("i= $i field= $field")
    }

    @ExperimentalTime
    @Test
    fun newTest(){
        prinp("\n==== newTest() Mulai ==== \n")
        data class Point(var x: Int, var y: Int= 198)
        measureTime { prin(Poin::class.si) }.also { prine("timeTaken= $it") }
        measureTime { prin(new(Point::class.si)) }.also { prine("timeTaken= $it") }
    }

    @ExperimentalTime
    @Test
    fun nativeNewTest(){
        prinp("\n==== nativeNewTest() Mulai ==== \n")
        data class Point(var x: Int, var y: Int= 198)
        measureTime { prin(Poin::class) }.also { prine("timeTaken= $it") }
        measureTime { prin(nativeNew(Point::class)) }.also { prine("timeTaken= $it") }
    }

    @Test
    fun typeAssignableTest(){
        prinp("\n==== typeAssignableTest() Mulai ==== \n")
        val everyone= Everyone()
        val american= American()

        val foodStore= FoodStore()
        val burgerStore= BurgerStore()

        val amer2: Consumer<Burger> = everyone
        val foodStr2: Producer<Food> = burgerStore
//        val burgStr2: Producer<Burger> = foodStore

        val consBurger= Consumer::class.si.createType(listOf(Burger::class.si.createType().simpleTypeProjection))
        val prodFood= Producer::class.si.createType(listOf(Food::class.si.createType().simpleTypeProjection))
        val prodBurger= Producer::class.si.createType(listOf(Burger::class.si.createType().simpleTypeProjection))

        val everyType= everyone::class.si.createType()
        val burgStrType= burgerStore::class.si.createType()
        val foodStrType= foodStore::class.si.createType()

        val isConsAssignable= consBurger.isAssignableFrom(everyType)
        val isProdAssignable= prodFood.isAssignableFrom(burgStrType)
        val isProdAssignable2= prodBurger.isAssignableFrom(foodStrType)

        prin("consBurger= $consBurger prodFood= $prodFood")
        prin("everyType= $everyType burgStrType= $burgStrType")
        prin("isConsAssignable= $isConsAssignable isProdAssignable= $isProdAssignable isProdAssignable2= $isProdAssignable2")
    }

    @Test
    fun enumTest(){
        prinp("\n==== enumTest() Mulai ==== \n")
        prin("Enum::class.si= ${Enum::class.si}")
        prin("Comparable::class.si= ${Comparable::class.si}")
        prin("Enum::class.si.typeParameters.first().upperBounds= ${Enum::class.si.typeParameters.firstOrNull()?.upperBounds}")
        prin("Enum::class.si.typeParameters.first()= ${Enum::class.si.typeParameters.firstOrNull()}")
        prin("En::class.si= ${En::class.si}")
        prin("En::class.si.classesTree= ${En::class.si.classesTree}")
        prin("En.A::class.si= ${En.A::class.si}")

        prin("\n============= En::class.si.classesTree =============\n")
        for((i, cls) in En::class.si.classesTree.withIndex()){
            prin("i= $i cls= $cls")
        }
        prin("\n============= En::class.si.members =============\n")
        for((i, member) in En::class.si.members.withIndex()){
            prin("i= $i member= $member")
        }

        prin("\n============= En.A::class.si.members =============\n")
        for((i, member) in En.A::class.si.members.withIndex()){
            prin("i= $i member= $member")
        }

        prin("\n============= En.A.enumValues =============\n")
        for((i, enu) in En.A.enumValues.withIndex()){
            prin("i= $i enum= $enu")
        }

        prin("\n============= String::class.si.classesTree =============\n")
        for((i, cls) in String::class.si.classesTree.withIndex()){
            prin("i= $i cls= $cls")
        }


        prin("En::class.si.isCopySafe= ${En::class.si.isCopySafe}")
        prin("En.A::class.si.isCopySafe= ${En.A::class.si.isCopySafe}")
        prin("\"aaf\"::class.si.isCopySafe= ${"aaf"::class.si.isCopySafe}")
        prin("AC::class.si.isCopySafe= ${AC::class.si.isCopySafe}")
    }

    @Test
    fun annotationTest(){
        prinp("\n==== annotationTest() Mulai ==== \n")
        prinp("\n==== annotationTest() Perantara Mulai ==== \n")
        for((i, member) in AC::class.si.members.withIndex()){
            prin("i= $i member= $member")
        }
        prinp("\n==== annotationTest() Perantara Selesai ==== \n")

        prin("\n============= Anotasi::class.si.members ===============\n")
        for((i, member) in Anotasi::class.si.members.withIndex()){
            prin("i= $i member= $member")
        }

        class Anot2(val a: Int, val b: Int): SiAnnotation
//        if(platform == Platform.JS){
            prin("hasil penambahan = ${AC::class.si.setAnnotation(Anot2(2, 3))}")
            prin("Anot2(2, 3)= ${Anot2(2, 3)}")
            prin("AC::class.si.annotations= ${AC::class.si.annotations}")
//        }

        prin("\n============= AC::class.si.annotations ===============\n")
        for((i, anot) in AC::class.si.annotations.withIndex()){
            prin("i= $i anot= $anot")
        }

        prin("AC::class.si.findAnnotation<Anot2>()= ${AC::class.si.findAnnotation<Anot2>()}")
        prin("AC::class.si.findAnnotation<Anotasi<*, *>>()= ${AC::class.si.findAnnotation<Anotasi<*, *>>()}")
        log(AC::class.si.findAnnotation<Anot2>())
    }

    @ExperimentalTime
    @Test
    fun annotationCallTest(){
        prinp("\n==== annotationCallTest() Mulai ==== \n")
        val ac= AC<BlaBla2>()
        if(platform == Platform.JS){
            val funAnot = nativeNew(FunAnot::class)!!
            val funAnot2 = nativeNew(FunAnot::class){ 2 }!!
            val func = AC::class.si.members.find { it.name == "someFun" }

            AC::class.si.annotateMember("someOtherFun", funAnot2)
            prin("func?.parameters?.first()?.isOptional= ${func?.parameters?.first()?.isOptional}")
            func?.callBy(mapOf(func.parameters.first() to 100))
            prin("func= $func")
            log(func)
            func?.setAnnotation(funAnot)
            prin("ac::someFun = ${ac::someFun}")
            prin("funAnot= $funAnot")
            log(funAnot)
            prin("FunAnot::class = ${FunAnot::class}")
            log(FunAnot::class)
        }
/*
        var time= measureTime {
            ac.callAnnotatedFunction<FunAnot> {
                prin("param= $it")
                10
            }
        }
        prine("time= $time")

        prin("\n==========================================\n")
        time= measureTime {
            ac.callAnnotatedFunction<FunAnot> {
//                prin("param= $it")
                10
            }
        }
        prine("time= $time")
        prin("\n==========================================\n")
        time= measureTime {
            ac.callAnnotatedFunction<FunAnot>({
                prin("it.a= ${it.a} it= $it")
                it.a == 2
            }){
                prin("param= $it")
                when(it.name){
                    "x" -> 1018
                    "az" -> 187
                    else -> null
                }
            }
        }
        prine("time= $time")
 */
/*
        val poin= Poin(y= 11)
        time= measureTime {
            ac.callAnnotatedFunctionWithParamContainer(FunAnot::class, poin){ it.a == 101 }
        }
        prine("time= $time")

        time= measureTime {
            ac.callAnnotatedFunctionWithParamContainer(FunAnot::class, poin){ it.a == 2 }
        }
        prine("time= $time")
 */
    }

    @Test
    fun renameTest(){
        prinp("\n==== renameTest() Mulai ==== \n")
        val poin= Poin(y=101)
        if(platform == Platform.JS){
            poin::class.si.annotateMember("aa_diPoin", SiRename("aa_aa_diPoin"))
        }
        prin(poin::class.si.members.find { it.name == "aa_diPoin" }?.renamedName)
    }

    @Test
    fun interfaceTest(){
        prin("BlaBlaInt::class.isInterface= ${BlaBlaInt::class.isInterface}")
        prin("AC::class.isInterface= ${AC::class.isInterface}")
    }

    //======Simulation===========

    @Test
    fun boundClone(){
        val bound= inboundList_created[0] //Bound(1, "invoice", "date")
        val product= productList_full[0]
        val productList= productList_full.toList()
        val boundProd= bound.productList ?: ArrayList()
        prin(bound.nativeClone())
        prin(product.nativeClone().also {
            prin("product.unit?.name= ${it.unit?.name}")
            prin("product.category?.name= ${it.category?.name}")
        })
        prin(productList.nativeClone())
        prin(boundProd.nativeClone())
    }

    @Test
    fun boundProperty(){
        SidevLibConfig.java7SupportEnabled= false
        val boundProd= inboundList_created[0].productList ?: ArrayList()
        prin("boundProd::class.isCollection= ${boundProd::class.isCollection}")
        for((i, prop) in boundProd.fieldValuesTree.withIndex()){
            prin("i= $i prop= $prop")
        }
        prin(boundProd.nativeClone())
//        prin(boundProd.first().nativeClone())
    }
}