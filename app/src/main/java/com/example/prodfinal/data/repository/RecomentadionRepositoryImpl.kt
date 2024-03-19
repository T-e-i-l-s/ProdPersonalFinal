package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.remote.RecomendationsApi
import com.example.prodfinal.domain.model.RecomendationModel

class RecomentadionRepositoryImpl {
    fun getRecomendations(
        context: Context,
        lat: Double,
        lon: Double,
        onFinish: (MutableList<RecomendationModel>) -> Unit
    ) {
        RecomendationsApi().getRecomendations(context, lat, lon, onFinish)
    }
}