package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.remote.WeatherApi
import com.example.prodfinal.domain.model.WeatherModel

class WeatherRepositoryImpl {
    fun getWeather(
        context: Context,
        lat: Double,
        lon: Double,
        onFinish: (result: WeatherModel) -> Unit
    ) {
        WeatherApi().getWeather(context, lat, lon, onFinish)
    }
}