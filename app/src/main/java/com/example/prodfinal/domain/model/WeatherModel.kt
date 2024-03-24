package com.example.prodfinal.domain.model

data class WeatherModel(
    val city: String,
    val weather: String,
    val icon: String,
    val currentTemperature: String,
    val minTemperature: String,
    val maxTemperature: String,
    val feelsLike: String,
)
