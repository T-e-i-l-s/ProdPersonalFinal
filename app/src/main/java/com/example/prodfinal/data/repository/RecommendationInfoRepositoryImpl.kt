package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.api.RecommendationInfoApi
import com.example.prodfinal.data.source.RecomendationSource
import com.example.prodfinal.domain.model.FullRecomendationModel
import com.example.prodfinal.domain.state.LoadingState

class RecommendationInfoRepositoryImpl {
    fun getRecommendationInfo (
        context: Context,
        fsqId: String,
        onFinish: (FullRecomendationModel) -> Unit
    ) {
        if (RecomendationSource().isSaved(context, fsqId)) {
            onFinish(RecomendationSource().getRecomendationData(context, fsqId))
        } else {
            RecommendationInfoApi().getRecommendationInfo(context, fsqId, onFinish)
        }
    }
}