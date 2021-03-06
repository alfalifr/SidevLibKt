package com.sigudang.android.models

//import com.sigudang.android._Dummy.CourierType
//import com.sigudang.android._template.model.PictModel
//import com.sigudang.android._template.model.PictTextModel
import sidev.lib.reflex._simulation.models.CourierType
import sidev.lib.reflex._simulation.models.PictModel
//import java.io.Serializable

data class SendMethodModel(var name: String, var img: PictModel?, var id: String? = null, var type: CourierType= CourierType.THIRD_PARTY)
//    : Serializable
//    : PictTextModel(img, name)