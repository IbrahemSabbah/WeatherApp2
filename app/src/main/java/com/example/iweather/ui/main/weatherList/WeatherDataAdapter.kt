package com.example.iweather.ui.main.weatherList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.WeatherCityEntityDomain
import com.example.iweather.databinding.WeatherDataItemBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.example.iweather.ColorGradientUtil
import com.example.iweather.databinding.WeatherDescriptionDetailsBinding


class WeatherDataAdapter(private val layoutType: WeatherListFragment.ListViewType) :
    PagingDataAdapter<WeatherCityEntityDomain, WeatherDataAdapter.WeatherDataViewHolder>(
        diffCallback
    ) {

    var listViewType = this.layoutType

    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {

        getItem(position)?.let {
            holder.bindTo(it,listViewType)
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

        fun bindTo(item: WeatherCityEntityDomain, listViewType: WeatherListFragment.ListViewType) {
            this.layoutPosition
            weatherDataItemBinding.cityName.text = item.cityName
            weatherDataItemBinding.cityTemp.text = item.temp.addDegree()

            weatherDataItemBinding.root.background =
                ColorGradientUtil.generateColorGradient(absoluteAdapterPosition)
            weatherDataItemBinding.weathetIcon.load(item.weatherIcon) {
                transformations(CircleCropTransformation())
            }

            when (listViewType){
                WeatherListFragment.ListViewType.Grid -> {
                    weatherDataItemBinding.weatherDetails.visibility=View.GONE
                }
                WeatherListFragment.ListViewType.List -> {
                    val view=weatherDataItemBinding.weatherDetails.inflate()
                    val detailsBinding=WeatherDescriptionDetailsBinding.bind(view)
                    detailsBinding.weatherDescription.text=item.weatherDescription
                    detailsBinding.windSpeed.text = "${item.windsSpeed}km/h"
                    detailsBinding.feelLike.text = item.feelsLike.addDegree()
                    detailsBinding.humidty.text = item.humidity.addDegree()
                }
            }



        }
    }
}

fun String.addDegree(): String {
    return this + 0x00B0.toChar()
}

