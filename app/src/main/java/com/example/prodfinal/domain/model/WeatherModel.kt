package com.example.prodfinal.domain.model

data class WeatherModel(
    val city: String,
    val weather: String,
    val icon: String,
    val currentTemprature: String,
    val minTemprature: String,
    val maxTemprature: String,
    val feelsLike: String,
)
