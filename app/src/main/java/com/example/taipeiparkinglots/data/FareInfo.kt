package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class FareInfo(
    @SerializedName("Holiday")
    val holiday: List<Holiday?>?,
    @SerializedName("WorkingDay")
    val workingDay: List<WorkingDay?>?
)