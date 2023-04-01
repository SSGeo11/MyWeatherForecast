package com.example.myweathertest

object Constants {
    val conditionIcon = mapOf(
        "clear" to R.drawable.clear,
        "partly-cloudy" to R.drawable.partly_cloudy,
        "cloudy" to R.drawable.ic_cloudy ,
        "overcast" to R.drawable.overcast,
        "drizzle" to R.drawable.light_rain,
        "light-rain" to R.drawable.light_rain,
        "rain" to R.drawable.rain,
        "moderate-rain" to R.drawable.moderate_rain,
        "heavy-rain" to R.drawable.moderate_rain,
        "showers" to R.drawable.moderate_rain,
        "continuous-heavy-rain" to R.drawable.moderate_rain,
        "wet-snow" to R.drawable.wet_snow,
        "light-snow" to R.drawable.light_snow,
        "snow" to R.drawable.snow,
        "snow-showers" to R.drawable.snow_showers,
        "hail" to R.drawable.hail,
        "thunderstorm" to R.drawable.thunderstorm,
        "thunderstorm-with-rain" to R.drawable.thunderstorm,
        "thunderstorm-with-hail" to R.drawable.thunderstorm
        )
    val condition = mapOf(
        "clear" to R.string.clear,
        "partly-cloudy" to R.string.partly_cloudy,
        "cloudy" to R.string.cloudy ,
        "overcast" to R.string.overcast,
        "drizzle" to R.string.drizzle,
        "light-rain" to R.string.light_rain,
        "rain" to R.string.rain,
        "moderate-rain" to R.string.moderate_rain,
        "heavy-rain" to R.string.heavy_rain,
        "showers" to R.string.showers,
        "continuous-heavy-rain" to R.string.continuous_heavy_rain,
        "wet-snow" to R.string.wet_snow,
        "light-snow" to R.string.light_snow,
        "snow" to R.string.snow,
        "snow-showers" to R.string.snow_showers,
        "hail" to R.string.hail,
        "thunderstorm" to R.string.thunderstorm,
        "thunderstorm-with-rain" to R.string.thunderstorm_with_rain,
        "thunderstorm-with-hail" to R.string.thunderstorm_with_hail
    )
    val defaultCities = mutableListOf<City>(
        City(0, "Якутск", 62.027220, 129.732181),
        City(1, "Москва", 55.755863, 37.617700),
        City(2, "Санкт-Петербург", 59.938951, 30.315635))

    val windDir = mapOf(
        "nw" to "СВ",
        "n" to "С",
        "ne" to "СЗ",
        "e" to "В",
        "se" to "ЮВ",
        "s" to "Ю",
        "sw" to "ЮЗ",
        "w" to "Х",
        "c" to "Штиль"
    )
}
