package sidev.lib.reflex

import sidev.lib.reflex.SiReflexImpl

//Agar dapat dijalankan di Js.
enum class SiVisibility: SiReflex by SiReflexImpl() {
    PUBLIC,

    PROTECTED,

    INTERNAL,

    PRIVATE
}