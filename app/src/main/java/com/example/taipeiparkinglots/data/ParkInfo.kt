package com.example.taipeiparkinglots.data

/**
 * Google Map上要用到的資料
 *
 * @property id 停車場id
 * @property name 名稱
 * @property area 區
 * @property address 地址
 * @property totalCar 全部車位
 * @property tw97x TWD97二度分帶座標X
 * @property tw97y TWD97二度分帶座標Y
 * @property carAvailable 空車位：如果拿不到這項資訊或是資訊錯誤（值是負的），就顯示"-"；如果資訊正常（大於等於0）則顯示數字
 */
data class ParkInfo(
    val id: String,
    val name: String,
    val area: String,
    val address: String,
    val totalCar: Int,
    val tw97x: Double,
    val tw97y: Double,
    val carAvailable: Int? = null
)
