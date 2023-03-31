package com.example.myweathertest

data class Forecasts(
    val date: String,
    val parts: Parts,
    val temp_min: Int,
    val temp_max: Int,
    val temp_avg: Int,
    val feels_like: Int,
    val icon: String,
    val condition: String,
    val wind_speed: Double,
    val wind_dir: String,
    val pressure_mm: Int,
    val humidity: Int,
)
