package com.example.myweathertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myweathertest.ApiFactory.weatherApi
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
                City(0, "Yakutsk", 62.027220, 129.732181),
                City(1, "Moscow", 55.755863, 37.617700),
                City(2, "St.Peterburg", 59.938951, 30.315635))
        }


        binding.cityList.layoutManager = manager // Назначение LayoutManager для RecyclerView
        binding.cityList.adapter = adapter // Назначение адаптера для RecyclerView

        adapter.setOnItemClickListener(object : CityAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                binding.cityNameDetailsTV.text = adapter.data[position].name
                CoroutineScope(Dispatchers.IO).launch {
                    val weather = weatherApi.getWeatherForecast(adapter.data[position].lat, adapter.data[position].lon)
                    runOnUiThread {
                        binding.cityTempDetailsTV.text = weather.fact.temp.toString()
                        binding.conditionDetailsTV.text = weather.fact.condition
                        binding.feelsLikeDetailsTV.text = weather.fact.feels_like.toString()
                        Glide.with(this@MainActivity)
                            .load(Constants.condition.getValue(weather.fact.condition))
                            .into(binding.iconDetailsIV)
                    }
                }
            }
        })

    }
}