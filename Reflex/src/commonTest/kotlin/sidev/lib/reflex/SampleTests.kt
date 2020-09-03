package sidev.lib.reflex

import sidev.lib.`val`.RoundingMode
import sidev.lib.collection.common.arrayWrapperOf
import sidev.lib.collection.common.getValue
import sidev.lib.collection.lazy_list.CachedSequence
import sidev.lib.collection.lazy_list.LazyHashMap
import sidev.lib.collection.sequence.withLevel
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.number.*
import sidev.lib.reflex.core.createType
import sidev.lib.reflex.full.*
import sidev.lib.reflex.full.types.*
import sidev.lib.reflex.native.si
import kotlin.math.exp
import kotlin.math.log
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class SampleTests {
    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
    }

    @ExperimentalTime
    @Test
    fun reflexTest(){
        prin("reflexTest() Mulai ==== ")
        val siCls: SiClass<AC<*>>
        var timeTaken = measureTime { siCls = AC::class.si }
        prin("siCls= $siCls")
        prine("timeTaken = $timeTaken")

        timeTaken = measureTime {
            prin("\n============ AC::class.si.members ================\n")
            for((i, member) in AC::class.si.members.withIndex()){
                prin("i= $i member= $member")
            }
        }
        prine("timeTaken = $timeTaken")
    }

    @Test
    fun reflexTypeTest(){
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

    @ExperimentalTime
    @Test
    fun cloneTest(){
        val ac= AC<BlaBla2>(Poin(y = 199))
        prin("cloneTest() Mulai ====")
        val ac2: AC<BlaBla2>
        var timeTaken = measureTime { ac2 = ac.clone() }
        prine("timeTaken= $timeTaken")
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
        val ac= AC<BlaBla2>(Poin(y = 199))
        prin("nativeCloneTest() Mulai ====")
        val ac2: AC<BlaBla2>
        var timeTaken = measureTime { ac2 = ac.nativeClone() }
        prine("timeTaken= $timeTaken")
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

        prin("\n============= list2::class.classesTree ===============\n")
        for((i, cls) in list2::class.classesTree.withLevel().withIndex())
            prin("i= $i cls= $cls")

        prin("\n============= List Clone ===============\n")
        list1[0].poin.x= 16
        list1[1].poin.y= 32
        prin("list1[0].poin.x= ${list1[0].poin.x} list1[1].poin.x= ${list1[1].poin.x}")
        prin("list1[0].poin.y= ${list1[0].poin.y} list1[1].poin.y= ${list1[1].poin.y}")
    }

    @Test
    fun typeAssignableTest(){
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
    fun mathTest(){
        val int= 2.3 as Number
        val int2= (2 as Number / int)
        prin("int= $int2")

        prin(ipow(2, 3))
        prin(ipow(2, -2))
        prin("2.pow(-2)= ${2 pow -2}")
        prin("125 root 3= ${125 root 3}")
        prin("81 root 4= ${81 root 4}")
        prin("81 root 2= ${81 root 2}")
        prin("81.sqrt()= ${81.sqrt()}")
        prin("exp(2.0)= ${exp(2.0)}")
        prin(ipow(3, 4))
        prin(ipow(5, 5))
        log(2f, 3f)
        prin("10 log 1000= ${10 log 1000}")
        prin("3 log 81= ${3 log 81}")

        prin("223.1352= ${223.1352}")
        prin("223.1352.round()= ${223.1352.round()}")
        prin("-223.1352.round()= ${(-223.1352).round()}")
        prin("223.1352.round(-1)= ${223.1352.round(-1)}")
        prin("-223.1352.round(-1)= ${(-223.1352).round(-1)}")
        prin("223.1352.round(-2)= ${223.1352.round(-2)}")

        prin("-223.1352.round(-2, RoundingMode.UP)= ${(-223.1352).round(-2, RoundingMode.UP)}")
        prin("223.1352.round(-2, RoundingMode.UP)= ${(223.1352).round(-2, RoundingMode.UP)}")
        prin("-223.1352.round(-2, RoundingMode.CEIL)= ${(-223.1352).round(-2, RoundingMode.CEIL)}")
        prin("223.1352.round(-2, RoundingMode.CEIL)= ${(223.1352).round(-2, RoundingMode.CEIL)}")
        prin("-223.1352.round(-2, RoundingMode.DOWN)= ${(-223.1352).round(-2, RoundingMode.DOWN)}")
        prin("223.1352.round(-2, RoundingMode.DOWN)= ${(223.1352).round(-2, RoundingMode.DOWN)}")
        prin("-223.1352.round(-2, RoundingMode.FLOOR)= ${(-223.1352).round(-2, RoundingMode.FLOOR)}")
        prin("223.1352.round(-2, RoundingMode.FLOOR)= ${(223.1352).round(-2, RoundingMode.FLOOR)}")
        prin("223.1352.round(-2, RoundingMode.HALF_UP)= ${(223.1352).round(-2, RoundingMode.HALF_UP)}")
        prin("223.1352.round(-2, RoundingMode.HALF_DOWN)= ${(223.1352).round(-2, RoundingMode.HALF_DOWN)}")
        prin("223.1352[-2]= ${223.1352[-2]}")
    }


    fun ipow(base: Int, exp: Int): Int {
        prine("base= $base exp= $exp")
        if (exp == 0) return 1
        val temp = ipow(base, (exp / 2))
        return if (exp % 2 == 0) temp * temp else base * temp * temp
    }

    @Test
    fun commonListTest1(){
        val arrayWrap= arrayWrapperOf(1,3,5,6.778,"hkj")
        val array by arrayWrap
        array.forEach {
            prin("array foreeach it= $it")
        }
    }

    @Test
    fun commonListTest2(){
        println("\n============= BATAS CachedSequence ==============\n")
        val strSeq= sequenceOf("Aku", "Mau", "Makan")
        val strSeq2= sequenceOf("Kamu" , "Dan", "Dia")
        val lazySeq= CachedSequence<String>()
        lazySeq += "Halo"
        lazySeq += "Bro"
        lazySeq += strSeq
        lazySeq += strSeq2
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

        lazyMap += pairSeq
        lazyMap += pairSeq2

        println("\n============= BATAS LazyHashMap.iterator() ==============\n")
        for((i, data) in lazyMap.withIndex()){
            prin("i= $i data= $data")
        }

        val valueMau= lazyMap["Mau"]
        val valueIya= lazyMap["Iya"]
        val valueKamu= lazyMap["Kamu"]
        val valueTidur= lazyMap["Tidur"]
        val contains5= lazyMap.containsValue(5)
        val contains0= lazyMap.containsValue(0)
        val contains4= lazyMap.containsValue(4)

        prin("valueMau= $valueMau valueIya= $valueIya valueKamu= $valueKamu contains5= $contains5 contains0= $contains0 contains4= $contains4 valueTidur= $valueTidur")
    }
}