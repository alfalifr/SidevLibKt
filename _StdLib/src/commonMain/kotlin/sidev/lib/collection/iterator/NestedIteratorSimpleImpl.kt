package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral

/**
 * Digunakan untuk melakukan iterasi terhadap data yg memiliki banyak keturunan.
 * Sebagai contoh adalah [ViewGroup] pada Android yg punya banyak child view.
 *
 * Iterasi dilakukan menggunakan metode DEPTH-FIRST PRE-ORDER.
 */
abstract class NestedIteratorSimpleImpl<T>(startIterator: Iterator<T>?)
    : NestedIteratorImpl<T, T>(startIterator),
    NestedIteratorSimple<T>,
    SkippableIterator<T> {
    constructor(startIterable: Iterable<T>): this(startIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: T?): this((start as? Iterable<T>)?.iterator()){
        this.start= start
    }

    final override val activeInputLines: ArrayList<Iterator<T>>
        get() = super.activeOutputLines
    final override var activeInputIterator: Iterator<T>?
        get() = super.activeOutputIterator
        set(value) {
            super.activeOutputIterator= value
        }

    /*
    override val activeOutputLines: ArrayList<Iterator<T>>
        get() = super.activeInputLines
    override var activeOutputIterator: Iterator<T>?
        get() = super.activeInputIterator
        set(value) {
            super.activeInputIterator= value
        }
 */

    override fun getInputIterator(nowOutput: T): Iterator<T>? = getOutputIterator(nowOutput)
}