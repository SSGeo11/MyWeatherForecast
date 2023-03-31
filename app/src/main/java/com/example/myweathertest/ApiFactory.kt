package com.example.myweathertest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    val weatherClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("X-Yandex-API-Key", "1ee6e256-0ed5-435c-9b27-0cf907e9000c")
            .build()
        chain.proceed(request)
    }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .client(weatherClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherApi : WeatherApi = retrofit.create(WeatherApi::class.java)
}