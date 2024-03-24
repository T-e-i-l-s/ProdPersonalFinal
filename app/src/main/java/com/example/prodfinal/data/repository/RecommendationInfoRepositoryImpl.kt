package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.api.RecommendationInfoApi
import com.example.prodfinal.data.source.RecomendationSource
import com.example.prodfinal.domain.model.FullRecommendationModel

// Класс репозитория для RecommendationInfoApi

class RecommendationInfoRepositoryImpl {
    fun getRecommendationInfo(
        context: Context,
        fsqId: String,
        onFinish: (FullRecommendationModel) -> Unit
    ) {
        // Проверяем наличае данной локации в кеше
        if (RecomendationSource().isSaved(context, fsqId)) { // Локация было сохранена в кеше
            // Получаем данные из кеша
            onFinish(RecomendationSource().getRecomendationData(context, fsqId))
        } else {
            // Получаем данные из api
            RecommendationInfoApi().getRecommendationInfo(context, fsqId, onFinish)
        }
    }
}