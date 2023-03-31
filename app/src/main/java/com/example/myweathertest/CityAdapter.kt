package com.example.myweathertest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myweathertest.ApiFactory.weatherApi
import com.example.myweathertest.databinding.CityItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class PersonDiffUtil(
    private val oldList: List<City>,
    private val newList: List<City>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson.id == newPerson.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson == newPerson
    }
}

class CityAdapter: RecyclerView.Adapter<CityAdapter.CityViewHolder>() {


    var data: List<City> = emptyList()
        set(newValue) {
            val personDiffUtil = PersonDiffUtil(field, newValue)
            val personDiffUtilResult = DiffUtil.calculateDiff(personDiffUtil)
            field = newValue
            personDiffUtilResult.dispatchUpdatesTo(this@CityAdapter)
        }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class CityViewHolder(val binding: CityItemBinding) : RecyclerView.ViewHolder(binding.root)

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = data.size

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityItemBinding.inflate(inflater, parent, false)

        return CityViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = data[position]
        val context = holder.itemView.context
        val forecast : WeatherForecast
        CoroutineScope(Dispatchers.IO).launch {
            val weather = weatherApi.getWeatherForecast(city.lat, city.lon)
            runCatching {
                holder.binding.cityTempTV.text = weather.fact.temp.toString()
            }
        }




        with(holder.binding) {
            holder.binding.cityNameTV.text = city.name
        }
    }



}

