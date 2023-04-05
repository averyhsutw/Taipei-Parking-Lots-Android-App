package com.example.taipeiparkinglots

import com.example.taipeiparkinglots.data.AllAvailableResponse
import com.example.taipeiparkinglots.data.AllDescResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://tcgbusfs.blob.core.windows.net/blobtcmsv/"

private val client = OkHttpClient.Builder()
    .addInterceptor(
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    ).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {
    @GET("TCMSV_alldesc.json")
    suspend fun getAllDesc(): Response<AllDescResponse>

    @GET("TCMSV_allavailable.json")
    suspend fun getAllAvailable(): Response<AllAvailableResponse>
}

object ApiServiceManager {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}