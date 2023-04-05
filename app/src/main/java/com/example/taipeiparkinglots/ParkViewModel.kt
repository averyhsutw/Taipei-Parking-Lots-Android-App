package com.example.taipeiparkinglots

import android.util.Log
import androidx.lifecycle.*
import com.example.taipeiparkinglots.data.Park
import com.example.taipeiparkinglots.data.ParkInfo
import com.example.taipeiparkinglots.data.ParkOfAllAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainParkViewModel"

class ParkViewModel : ViewModel() {

    private var _allParkMap = MutableLiveData<Map<String, ParkInfo>>()
    val allParkMap: LiveData<Map<String, ParkInfo>> = _allParkMap

    fun reloadAllData() {
        fetchDataToMap()
        Log.d(TAG, "Data is Reloaded!")
    }

    private fun fetchDataToMap() = viewModelScope.launch {
        val descDeferred = async { loadAllDescription() }
        val availableDeferred = async { loadAllAvailable() }
        val cacheMap = mutableMapOf<String, ParkInfo>()
        val descParkList = descDeferred.await()
        for (park in descParkList) {
            if (park == null) {
                continue
            }
            val key = park.id!!
            val name = park.name.let {
                if (it.isNullOrEmpty()){
                    " -"
                }
                else {
                    it
                }
            }
            val area = park.area.let {
                if (it.isNullOrEmpty()) {
                    " -"
                }
                else {
                    it
                }
            }
            val address = park.address.let {
                if (it.isNullOrEmpty()) {
                    " -"
                }
                else {
                    it
                }
            }
            val parkInfo =
                ParkInfo(
                    id = key,
                    name = name,
                    area = area,
                    address = address,
                    totalCar = park.totalCar ?: 0,
                    tw97x = park.tw97x?.toDouble() ?: 0.0,
                    tw97y = park.tw97y?.toDouble() ?: 0.0
                )
            cacheMap[key] = parkInfo
        }
        val availableParkList = availableDeferred.await()
        for (park in availableParkList) {
            if (park == null) {
                continue
            }
            val id = park.id!!
            if (id in cacheMap.keys) {
                val parkInfoCopy = cacheMap[id]!!.copy(
                    carAvailable = park.availableCar
                )
                cacheMap[id] = parkInfoCopy
            }
        }
        _allParkMap.value = cacheMap
    }

    private suspend fun loadAllDescription(): List<Park?> {
        return try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceManager.service.getAllDesc()
            }
            Log.d(TAG, "load all description SUCCESS")
            response.body()?.data?.park ?: listOf()
        } catch (e: Exception) {
            Log.d(TAG, "load all description FAILED")
            e.printStackTrace()
            listOf()
        }
    }

    private suspend fun loadAllAvailable(): List<ParkOfAllAvailable?> {
        return try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceManager.service.getAllAvailable()
            }
            Log.d(TAG, "load all available SUCCESS")
            response.body()?.data?.park ?: listOf()
        } catch (e: Exception) {
            Log.d(TAG, "load all available FAILED")
            e.printStackTrace()
            listOf()
        }
    }
}