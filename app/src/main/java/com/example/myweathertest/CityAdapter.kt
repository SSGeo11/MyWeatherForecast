package com.example.myweathertest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myweathertest.ApiFactory.weatherApi
import com.example.myweathertest.databinding.CityItemBinding
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class CityDiffUtil(

    private val oldList: List<City>,
    private val newList: List<City>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCity = oldList[oldItemPosition]
        val newCity = newList[newItemPosition]
        return oldCity.id == newCity.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCity = oldList[oldItemPosition]
        val newCity = newList[newItemPosition]
        return oldCity == newCity
    }
}

class CityAdapter: RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }



    var data: List<City> = emptyList()
        set(newValue) {
            val cityDiffUtil = CityDiffUtil(field, newValue)
            val cityDiffUtilResult = DiffUtil.calculateDiff(cityDiffUtil)
            field = newValue
            cityDiffUtilResult.dispatchUpdatesTo(this@CityAdapter)
        }

    class CityViewHolder(val binding: CityItemBinding, listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityItemBinding.inflate(inflater, parent, false)

        return CityViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = data[position]
        val context = holder.itemView.context
        CoroutineScope(Dispatchers.IO).launch {
            val weather = weatherApi.getWeatherForecast(city.lat, city.lon)
            withContext(Dispatchers.Main) {
                holder.binding.cityTempTV.text = weather.fact.temp.toString()
            }
        }

        with(holder.binding) {
            holder.binding.cityNameTV.text = city.name
        }
    }



}

