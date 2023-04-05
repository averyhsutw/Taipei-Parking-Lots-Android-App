package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class ScoketStatus(
    @SerializedName("spot_abrv")
    val spotAbrv: String?,
    @SerializedName("spot_status")
    val spotStatus: String?
)