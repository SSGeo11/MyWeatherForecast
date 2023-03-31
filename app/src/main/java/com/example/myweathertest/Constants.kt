package com.example.myweathertest

object Constants {
    val condition = mapOf(
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
    val defaultCities = mutableListOf<City>(
        City(0, "Yakutsk", 62.027220, 129.732181),
        City(1, "Moscow", 55.755863, 37.617700),
        City(2, "St.Peterburg", 59.938951, 30.315635))
}
