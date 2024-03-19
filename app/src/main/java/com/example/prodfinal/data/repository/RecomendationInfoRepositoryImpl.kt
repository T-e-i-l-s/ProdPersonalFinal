package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.remote.RecomendationInfoApi
import com.example.prodfinal.domain.model.FullRecomendationModel

class RecomendationInfoRepositoryImpl {
    fun getRecomendations (
        context: Context,
        fsqId: String,
        onFinish: (FullRecomendationModel) -> Unit
    ) {
        RecomendationInfoApi().getRecomendationInfo(context, fsqId, onFinish)
    }
}