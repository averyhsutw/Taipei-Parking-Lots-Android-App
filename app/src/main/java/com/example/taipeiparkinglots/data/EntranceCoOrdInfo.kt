package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class EntranceCoOrdInfo(
    @SerializedName("Address")
    val address: String?,
    @SerializedName("Xcod")
    val xCod: String?,
    @SerializedName("Ycod")
    val yCod: String?
)