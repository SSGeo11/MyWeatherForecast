package com.example.myweathertest

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    val fact: Fact,
    val forecasts: List<Forecasts>
)
