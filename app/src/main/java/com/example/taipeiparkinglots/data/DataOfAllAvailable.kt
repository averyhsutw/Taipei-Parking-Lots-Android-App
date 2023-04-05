package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class DataOfAllAvailable(
    @SerializedName("park")
    val park: List<ParkOfAllAvailable?>?,
    @SerializedName("UPDATETIME")
    val updateTime: String?
)