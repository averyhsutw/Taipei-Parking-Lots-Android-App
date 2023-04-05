package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class AllAvailableResponse(
    @SerializedName("data")
    val data: DataOfAllAvailable?
)