package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class ParkOfAllAvailable(
    @SerializedName("availablebus")
    val availableBus: Int?,
    @SerializedName("availablecar")
    val availableCar: Int?,
    @SerializedName("availablemotor")
    val availableMotor: Int?,
    @SerializedName("ChargeStation")
    val chargeStation: ChargeStation?,
    @SerializedName("id")
    val id: String?
)