package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.api.RecommendationsApi
import com.example.prodfinal.domain.model.RecomendationModel

// Класс репозитория для RecommendationsApi
class RecommendationsRepositoryImpl {
    fun getRecommendations(
        context: Context,
        lat: Double,
        lon: Double,
        onFinish: (MutableList<RecomendationModel>) -> Unit
    ) {
        RecommendationsApi().getRecommendations(context, lat, lon, onFinish)
    }
}