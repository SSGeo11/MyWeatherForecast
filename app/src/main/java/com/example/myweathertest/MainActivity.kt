package com.example.myweathertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweathertest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.myweathertest.CityService



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CityAdapter
    private val cityService: CityService
        get() = (applicationContext as App).cityService

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

        val manager = LinearLayoutManager(this) // LayoutManager
        adapter = CityAdapter() // Создание объекта
        try {
            adapter.data = cityService.getCities() // Заполнение данными
        }catch(except : Exception){
            adapter.data =  mutableListOf<City>(
                City(1, "Yakutsk", 62.027220, 129.732181),
                City(2, "Moscow", 55.755863, 37.617700),
                City(3, "St.Peterburg", 59.938951, 30.315635))
        }


        binding.cityList.layoutManager = manager // Назначение LayoutManager для RecyclerView
        binding.cityList.adapter = adapter // Назначение адаптера для RecyclerView

    }
}