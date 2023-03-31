package com.example.myweathertest

import androidx.lifecycle.ViewModel

class CityViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }


}