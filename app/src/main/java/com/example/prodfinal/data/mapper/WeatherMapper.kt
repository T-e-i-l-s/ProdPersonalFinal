package com.example.prodfinal.data.mapper

import com.example.prodfinal.domain.model.WeatherModel
import kotlin.math.roundToInt

// Класс маппера погоды

class WeatherMapper {
    fun invoke(weather: WeatherModel): WeatherModel {
        // Корректируем и дополняем необходимые данные, а затем возвращаем
        return WeatherModel(
            weather.city,
            weather.weather,
            weather.icon,
            "" + weather.currentTemprature.toDouble().roundToInt() + "°C",
            "" + weather.minTemprature.toDouble().roundToInt() + "°C",
            "" + weather.maxTemprature.toDouble().roundToInt() + "°C",
            "Ощущается как ${weather.feelsLike.toDouble().roundToInt()}°C",
        )
    }
}