package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class ChargeStation(
    @SerializedName("scoketStatusList")
    val scoketStatusList: List<ScoketStatus?>?
)