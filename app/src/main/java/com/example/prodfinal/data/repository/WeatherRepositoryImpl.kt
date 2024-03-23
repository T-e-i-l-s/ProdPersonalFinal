package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.api.WeatherApi
import com.example.prodfinal.domain.model.WeatherModel

// Класс репозитория для WeatherApi

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