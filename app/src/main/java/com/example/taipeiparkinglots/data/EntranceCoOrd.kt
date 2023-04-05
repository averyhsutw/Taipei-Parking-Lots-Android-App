package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class EntranceCoOrd(
    @SerializedName("EntrancecoordInfo")
    val entranceCoOrdInfo: List<EntranceCoOrdInfo?>?
)