package com.example.myweathertest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myweathertest.ApiFactory.weatherApi
import com.example.myweathertest.databinding.FragmentCityDetailsBinding
import com.example.myweathertest.databinding.FragmentCityListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityDetailsFragment : Fragment() {

    private lateinit var adapter: DayAdapter
//    private val cityService: CityService
//        get() = (requireActivity().applicationContext as App).cityService
    private lateinit var navController: NavController
    private lateinit var binding: FragmentCityDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_details, container, false)
    }

    interface Callbacks {
        fun onDataRemove()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityTempTV : TextView = view.findViewById(R.id.cityTempDetailsTV)
        val cityFeelsLikeTV : TextView = view.findViewById(R.id.cityFeelsLikeDetailsTV)
        val cityConditionTV : TextView = view.findViewById(R.id.cityConditionDetailsTV)
        val morningTempTV : TextView = view.findViewById(R.id.morningTempTV)
        val morningIconTV : ImageView = view.findViewById(R.id.morningIconIV)
        val morningFeelsLikeTV : TextView = view.findViewById(R.id.morningFeelsLikeTV)
        val morningWindTV : TextView = view.findViewById(R.id.morningWindTV)
        val dayTempTV : TextView = view.findViewById(R.id.dayTempTV)
        val dayIconTV : ImageView = view.findViewById(R.id.dayIconIV)
        val dayFeelsLikeTV : TextView = view.findViewById(R.id.dayFeelsLikeTV)
        val dayWindTV : TextView = view.findViewById(R.id.dayWindTV)
        val eveningTempTV : TextView = view.findViewById(R.id.eveningTempTV)
        val eveningIconTV : ImageView = view.findViewById(R.id.eveningIconIV)
        val eveningFeelsLikeTV : TextView = view.findViewById(R.id.eveningFeelsLikeTV)
        val eveningWindTV : TextView = view.findViewById(R.id.eveningWindTV)
        val nightTempTV : TextView = view.findViewById(R.id.nightTempTV)
        val nightIconTV : ImageView = view.findViewById(R.id.nightIconIV)
        val nightFeelsLikeTV : TextView = view.findViewById(R.id.nightFeelsLikeTV)
        val nightWindTV : TextView = view.findViewById(R.id.nightWindTV)

        val manager = LinearLayoutManager(requireActivity()) // LayoutManager
        adapter = DayAdapter() // Создание объекта

        val position = arguments?.getInt("Position")

        navController = findNavController()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            navController.popBackStack(R.id.cityListFragment, false)
            removeData()
            Log.i("Test", "Back")
        }

        binding = FragmentCityDetailsBinding.inflate(layoutInflater)
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
            val weather = weatherApi.getWeatherForecast(Constants.defaultCities[position!!].lat, Constants.defaultCities[position].lon)
            withContext(Dispatchers.Main) {
                adapter.data = weather.forecasts
                cityTempTV.text = weather.fact.temp.toString()
                cityFeelsLikeTV.text = "Ощущается как: ${weather.fact.feels_like}"
                cityConditionTV.setText(Constants.condition.getValue(weather.fact.condition))

                Log.i("JSON", weather.toString())
                morningTempTV.text = weather.forecasts[0].parts.morning.temp_avg.toString()
                morningFeelsLikeTV.text = weather.forecasts[0].parts.morning.feels_like.toString()
                morningWindTV.text = "${weather.forecasts[0].parts.morning.wind_speed} м/с\n${Constants.windDir.getValue(weather.forecasts[0].parts.morning.wind_dir)}"
                Glide.with(this@CityDetailsFragment)
                    .load(Constants.conditionIcon.getValue(weather.forecasts[0].parts.morning.condition))
                    .into(morningIconTV)

                dayTempTV.text = weather.forecasts[0].parts.day.temp_avg.toString()
                dayFeelsLikeTV.text = weather.forecasts[0].parts.day.feels_like.toString()
                dayWindTV.text = "${weather.forecasts[0].parts.day.wind_speed} м/с\n${Constants.windDir.getValue(weather.forecasts[0].parts.day.wind_dir)}"
                Glide.with(this@CityDetailsFragment)
                    .load(Constants.conditionIcon.getValue(weather.forecasts[0].parts.day.condition))
                    .into(dayIconTV)

                eveningTempTV.text = weather.forecasts[0].parts.evening.temp_avg.toString()
                eveningFeelsLikeTV.text = weather.forecasts[0].parts.evening.feels_like.toString()
                eveningWindTV.text = "${weather.forecasts[0].parts.evening.wind_speed} м/с\n${Constants.windDir.getValue(weather.forecasts[0].parts.evening.wind_dir)}"
                Glide.with(this@CityDetailsFragment)
                    .load(Constants.conditionIcon.getValue(weather.forecasts[0].parts.evening.condition))
                    .into(eveningIconTV)


                nightTempTV.text = weather.forecasts[1].parts.night.temp_avg.toString()
                nightFeelsLikeTV.text = weather.forecasts[1].parts.night.feels_like.toString()
                nightWindTV.text = "${weather.forecasts[1].parts.night.wind_speed} м/с\n${Constants.windDir.getValue(weather.forecasts[1].parts.night.wind_dir)}"
                Glide.with(this@CityDetailsFragment)
                    .load(Constants.conditionIcon.getValue(weather.forecasts[1].parts.night.condition))
                    .into(nightIconTV)

            }
        }

        val dayRV : RecyclerView = view.findViewById(R.id.dayRV)
        dayRV.layoutManager = manager
        dayRV.adapter = adapter

    }



    private var callbacks: CityDetailsFragment.Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as? CityDetailsFragment.Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun removeData() {
        callbacks?.onDataRemove()
    }

}