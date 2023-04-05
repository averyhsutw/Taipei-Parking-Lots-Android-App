package com.example.taipeiparkinglots.data


import com.google.gson.annotations.SerializedName

data class Park(
    @SerializedName("AED_Equipment")
    val aedEquipment: String?,
    @SerializedName("Accessibility_Elevator")
    val accessibilityElevator: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("area")
    val area: String?,
    @SerializedName("CellSignal_Enhancement")
    val cellSignalEnhancement: String?,
    @SerializedName("ChargingStation")
    val chargingStation: String?,
    @SerializedName("Child_Pickup_Area")
    val childPickupArea: String?,
    @SerializedName("EntranceCoord")
    val entranceCoOrd: EntranceCoOrd?,
    @SerializedName("FareInfo")
    val fareInfo: FareInfo?,
    @SerializedName("Handicap_First")
    val handicapFirst: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("payex")
    val payEx: String?,
    @SerializedName("Phone_Charge")
    val phoneCharge: String?,
    @SerializedName("Pregnancy_First")
    val pregnancyFirst: String?,
    @SerializedName("serviceTime")
    val serviceTime: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("Taxi_OneHR_Free")
    val taxiOneHRFree: String?,
    @SerializedName("tel")
    val tel: String?,
    @SerializedName("totalbike")
    val totalBike: Int?,
    @SerializedName("totalbus")
    val totalBus: Int?,
    @SerializedName("totalcar")
    val totalCar: Int?,
    @SerializedName("totallargemotor")
    val totalLargeMotor: String?,
    @SerializedName("totalmotor")
    val totalMotor: Int?,
    @SerializedName("tw97x")
    val tw97x: String?,
    @SerializedName("tw97y")
    val tw97y: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("type2")
    val type2: String?
)