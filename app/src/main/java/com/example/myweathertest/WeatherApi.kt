package com.example.myweathertest

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {
    @GET("v2/forecast?")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "ru_RU",
        @Query("limit") limit: Int = 7
    ) : WeatherForecast
}