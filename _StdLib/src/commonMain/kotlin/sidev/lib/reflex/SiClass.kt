package sidev.lib.reflex

/**
 * File yg berisi komponen refleksi yg dapat digunakan scr multi-platform.
 */

interface SiClass<T: Any>: SiClassifier, SiAnnotatedElement {
    /** Nama lengkap dari kelas ini. `null` jika anonymous. */
    val qualifiedName: String?

    /** Nama simpel dari kelas ini. `null` jika anonymous. */
    val simpleName: String?

    /**
     * Property dan Function yg dideklarasikan pada kelas ini
     * dan supertype yg dapat diakses dari kelas ini.
     * Tidak termasuk konstruktor.
     */
    val members: Collection<SiCallable<*>>

    /** Konstruktor yg dideklarasikan pada kelas ini. */
    val constructors: List<SiFunction<T>>

    /** Generic type parameter pada kelas ini. */
    val typeParameters: List<SiTypeParameter>

    /** Immediate supertypes. */
    val supertypes: List<SiType>

    /** Visibilitas akses dari komponen SiReflex ini, default public. */
    val visibility: SiVisibility

    val isAbstract: Boolean
}