package com.example.myweathertest

import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
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



class MainActivity : AppCompatActivity(), CityListFragment.Callbacks, CityDetailsFragment.Callbacks {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CityAdapter
    private val cityService: CityService
        get() = (applicationContext as App).cityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = ""



    }

    override fun onDataSelected(data: String) {
        val toolbarTitle: TextView = findViewById(R.id.toolbar_title)
        toolbarTitle.text = data
    }

    override fun onDataRemove() {
        val toolbarTitle: TextView = findViewById(R.id.toolbar_title)
        toolbarTitle.text = ""
    }
}