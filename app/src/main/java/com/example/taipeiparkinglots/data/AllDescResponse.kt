package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class AllDescResponse(
    @SerializedName("data")
    val data: Data?
)