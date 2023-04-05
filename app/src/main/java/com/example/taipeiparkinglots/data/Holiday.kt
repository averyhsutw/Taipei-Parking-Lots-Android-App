package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class Holiday(
    @SerializedName("Fare")
    val fare: String?,
    @SerializedName("Period")
    val period: String?
)