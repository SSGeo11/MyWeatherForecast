package com.example.myweathertest

import android.app.Application
import com.example.myweathertest.CityService

class App : Application() {
    val cityService = CityService()
}