package com.example.myweathertest

import android.os.Bundle

class CityService {
    private var cities = mutableListOf<City>(
        City(1, "Yakutsk", 62.027220, 129.732181),
        City(2, "Moscow", 55.755863, 37.617700),
        City(3, "St.Peterburg", 59.938951, 30.315635)) // Все пользователи

    fun getCities(): List<City> = cities


}