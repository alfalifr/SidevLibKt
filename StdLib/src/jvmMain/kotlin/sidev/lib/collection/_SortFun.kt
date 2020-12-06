@file:JvmName("_SortFunJvm")
package sidev.lib.collection

import sidev.lib.`val`.Order
import java.util.*
import java.util.Arrays.sort as jsort
import java.util.Collections.sort as jsortList


actual fun <T: Comparable<T>> Array<T>.fastSort(from: Int, until: Int, order: Order) =
    if(order == Order.ASC) jsort(this, from, until)
    else jsort(this, from, until, Collections.reverseOrder())
actual fun CharArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun ByteArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun ShortArray.fastSort(from: Int, until: Int, order: Order) {
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun IntArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun LongArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun FloatArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun DoubleArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun <T: Comparable<T>> MutableList<T>.fastSort(order: Order) =
    if(order == Order.ASC) jsortList(this)
    else jsortList(this, Collections.reverseOrder())

/*
===============
Comparison
===============
 */
/*
fun <T: Comparable<T>> Collection<T>.sortedWith(f: (T, T) -> Boolean = ::asc): List<T> = this.toMutableList().sort(f)
/**
 * Mengurutkan isi dari `this.extension` [List] jika isinya merupakan turunan [Comparable]
 * dan mengembalikan `this.extension` sehingga dapat di-chain.
 */
fun <L: MutableList<T>, T: Comparable<T>> L.sort(f: (T, T) -> Boolean = ::asc): L {
    for(i in indices)
        for(u in i+1 until size){
            val isOrderTrue = try{ f(this[i], this[u]) }
            catch (e: ClassCastException){
                val ascFun: (T, T) -> Boolean = ::asc
                val descFun: (T, T) -> Boolean = ::desc
                when(f){
                    ascFun -> univAsc(this[i], this[u])
                    descFun -> univDesc(this[i], this[u])
                    else -> throw UndefinedDeclarationExc(undefinedDeclaration = "${this[i]::class}.compareTo(${this[u]::class})")
                }
            }
            if(!isOrderTrue){
                val temp= this[i]
                this[i]= this[u]
                this[u]= temp
            }
        }
    return this
}
/ *
/** Sama dg [MutableList.sort] di atas, namun digunakan pada [Array]. */
fun <T: Comparable<T>> Array<T>.sort(f: (T, T) -> Boolean = ::asc): Array<T> {
    for(i in indices)
        for(u in i+1 until size){
            val isOrderTrue = try{ f(this[i], this[u]) }
            catch (e: ClassCastException){
                val ascFun: (T, T) -> Boolean = ::asc
                val descFun: (T, T) -> Boolean = ::desc
                when(f){
                    ascFun -> univAsc(this[i], this[u])
                    descFun -> univDesc(this[i], this[u])
                    else -> throw UndefinedDeclarationExc(undefinedDeclaration = "${this[i]::class}.compareTo(${this[u]::class})")
                }
            }
            if(!isOrderTrue){
                val temp= this[i]
                this[i]= this[u]
                this[u]= temp
            }
        }
    return this
}
 */