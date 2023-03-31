package com.example.myweathertest

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myweathertest.databinding.CityItemBinding
import com.example.myweathertest.databinding.DayItemBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DayDiffUtil(

    private val oldList: List<Forecasts>,
    private val newList: List<Forecasts>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDay = oldList[oldItemPosition]
        val newDay = newList[newItemPosition]
        return oldDay.date == newDay.date
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDay = oldList[oldItemPosition]
        val newDay = newList[newItemPosition]
        return oldDay == newDay
    }
}

class DayAdapter : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
    class DayViewHolder(val binding: DayItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    var data: List<Forecasts> = emptyList()
        set(newValue) {
            val dayDiffUtil = DayDiffUtil(field, newValue)
            val dayDiffUtilResult = DiffUtil.calculateDiff(dayDiffUtil)
            field = newValue
            dayDiffUtilResult.dispatchUpdatesTo(this@DayAdapter)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DayItemBinding.inflate(inflater, parent, false)

        return DayAdapter.DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = data[position]
        Log.i("Test", day.toString())
        val context = holder.itemView.context
        with(holder.binding){
            holder.binding.daysDateTV.text = day.date
            holder.binding.daysTempTV.text = "${day.parts.day.temp_max} / ${day.parts.night.temp_min}"
            Picasso.get().load(Constants.conditionIcon.getValue(day.parts.day.condition)).into(holder.binding.daysIconIV)
        }
    }

    override fun getItemCount(): Int = data.size

}