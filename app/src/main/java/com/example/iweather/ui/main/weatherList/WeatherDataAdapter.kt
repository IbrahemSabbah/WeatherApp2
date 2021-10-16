package com.example.iweather.ui.main.weatherList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.WeatherCityEntityDomain
import com.example.iweather.databinding.WeatherDataItemBinding
import android.graphics.drawable.GradientDrawable
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import com.example.iweather.ColorGradientUtil


class WeatherDataAdapter :
    PagingDataAdapter<WeatherCityEntityDomain, WeatherDataAdapter.WeatherDataViewHolder>(
        diffCallback
    ) {
    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindTo(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        return WeatherDataViewHolder(
            WeatherDataItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<WeatherCityEntityDomain>() {
            override fun areItemsTheSame(
                oldItem: WeatherCityEntityDomain,
                newItem: WeatherCityEntityDomain
            ): Boolean {
                return newItem.temp == oldItem.temp
            }

            override fun areContentsTheSame(
                oldItem: WeatherCityEntityDomain,
                newItem: WeatherCityEntityDomain
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class WeatherDataViewHolder(private val weatherDataItemBinding: WeatherDataItemBinding) :
        RecyclerView.ViewHolder(
            weatherDataItemBinding.root
        ) {

        fun bindTo(item: WeatherCityEntityDomain) {
            weatherDataItemBinding.cityName.text = item.cityName
            weatherDataItemBinding.cityTemp.text = item.temp

            weatherDataItemBinding.root.background = ColorGradientUtil.generateColorGradient(absoluteAdapterPosition)
            weatherDataItemBinding.weathetIcon.load(item.weatherIcon){
                transformations( CircleCropTransformation())
            }
        }
    }
}