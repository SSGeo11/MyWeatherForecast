package com.example.myweathertest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweathertest.databinding.FragmentCityListBinding


class CityListFragment : Fragment() {

    private lateinit var binding: FragmentCityListBinding
    private lateinit var navContoller: NavController
    private lateinit var adapter: CityAdapter
    private val cityService: CityService
        get() = (requireActivity().applicationContext as App).cityService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_list, container, false)
    }
    interface Callbacks : CityDetailsFragment.Callbacks {
        fun onDataSelected(data: String)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navContoller = findNavController()

        val manager = LinearLayoutManager(requireActivity()) // LayoutManager
        adapter = CityAdapter() // Создание объекта
        try {
            adapter.data = Constants.defaultCities // Заполнение данными
        }catch(except : Exception){
            adapter.data =  mutableListOf<City>(
                City(0, "Yakutsk", 62.027220, 129.732181),
                City(1, "Moscow", 55.755863, 37.617700),
                City(2, "St.Peterburg", 59.938951, 30.315635))
        }

        val cityRV : RecyclerView = view.findViewById(R.id.cityRV)
        cityRV.layoutManager = manager
        cityRV.adapter = adapter

        val bundle = Bundle()

        adapter.setOnItemClickListener(object : CityAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                bundle.putInt("Position", position)
                putData(adapter.data[position].name)
                Log.i("Test", position.toString())
                findNavController().navigate(R.id.cityDetailsFragment, bundle)
            }
        })
    }
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as? Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun putData(data: String) {
        // Передайте данные в активность с помощью интерфейса обратного вызова
        callbacks?.onDataSelected(data)
    }

}