package com.example.myweathertest

data class Fact(
    val temp: Int,
    val feels_like: Int,
    val icon: String,
    val condition: String,
    val wind_speed: Double,
    val wind_dir: String,
    val pressure_mm: Int,
    val humidity: Int,
)
