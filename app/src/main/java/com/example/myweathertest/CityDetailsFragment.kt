package com.example.myweathertest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweathertest.ApiFactory.weatherApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityDetailsFragment : Fragment() {

//    private lateinit var adapter: CityAdapter
//    private val cityService: CityService
//        get() = (requireActivity().applicationContext as App).cityService
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityNameTV : TextView = view.findViewById(R.id.cityNameDetailsTV)
        val cityTempTV : TextView = view.findViewById(R.id.cityTempDetailsTV)
        val cityFeelsLikeTV : TextView = view.findViewById(R.id.cityFeelsLikeDetailsTV)
        val cityConditionTV : TextView = view.findViewById(R.id.cityConditionDetailsTV)

        val position = arguments?.getInt("Position")

        navController = findNavController()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            navController.popBackStack(R.id.cityListFragment, false)
            Log.i("Test", "Back")
        }

//        adapter = CityAdapter() // Создание объекта
//        try {
//            adapter.data = Constants.defaultCities // Заполнение данными
//        }catch(except : Exception){
//            adapter.data =  mutableListOf<City>(
//                City(0, "Yakutsk", 62.027220, 129.732181),
//                City(1, "Moscow", 55.755863, 37.617700),
//                City(2, "St.Peterburg", 59.938951, 30.315635))
//        }

        CoroutineScope(Dispatchers.IO).launch {
            cityNameTV.text = Constants.defaultCities[position!!].name
            val weather = weatherApi.getWeatherForecast(Constants.defaultCities[position].lat, Constants.defaultCities[position].lon)
            withContext(Dispatchers.Main) {
                cityTempTV.text = weather.fact.temp.toString()
                cityFeelsLikeTV.text = weather.fact.feels_like.toString()
                cityConditionTV.text = weather.fact.condition
            }
        }


    }

}