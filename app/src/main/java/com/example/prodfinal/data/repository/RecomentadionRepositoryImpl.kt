package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.remote.RecomendationApi
import com.example.prodfinal.data.remote.WeatherApi
import com.example.prodfinal.domain.model.RecomendationModel
import com.example.prodfinal.domain.model.WeatherModel

class RecomentadionRepositoryImpl {
    fun getRecomendations(
        context: Context,
        lat: Double,
        lon: Double,
        onFinish: (MutableList<RecomendationModel>) -> Unit
    ) {
        RecomendationApi().getRecomendations(context, lat, lon, onFinish)
    }
}