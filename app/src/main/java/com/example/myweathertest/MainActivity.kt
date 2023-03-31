package com.example.myweathertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myweathertest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val weatherApi = retrofit.create(WeatherApi::class.java)

        binding.getB.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val weather = weatherApi.getWeatherForecast(62.027220, 129.732181)
                runOnUiThread {
                    binding.tempTV.text = weather.fact.temp.toString()
                    binding.feelsLikeTV.text = weather.fact.feels_like.toString()
                }
            }
        }

    }
}