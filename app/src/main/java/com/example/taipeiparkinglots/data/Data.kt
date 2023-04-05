package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("park")
    val park: List<Park?>?,
    @SerializedName("UPDATETIME")
    val updateTime: String?
)