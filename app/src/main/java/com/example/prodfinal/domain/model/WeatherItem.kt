package com.example.prodfinal.domain.model

data class WeatherItem(
    val city: String,
    val weather: String,
    val currentTemprature: String,
    val minTemprature: String,
    val maxTemprature: String,
    val feelsLike: String,
)
