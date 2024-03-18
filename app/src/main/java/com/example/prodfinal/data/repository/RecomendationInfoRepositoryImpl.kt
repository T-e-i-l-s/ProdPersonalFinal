package com.example.prodfinal.data.repository

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.data.remote.RecomendationApi
import com.example.prodfinal.data.remote.RecomendationInfoApi
import com.example.prodfinal.data.remote.WeatherApi
import com.example.prodfinal.domain.model.FullRecomendationModel
import com.example.prodfinal.domain.model.RecomendationModel
import com.example.prodfinal.domain.model.WeatherModel

class RecomendationInfoRepositoryImpl {
    fun getRecomendations (
        context: Context,
        fsqId: String,
        onFinish: (FullRecomendationModel) -> Unit
    ) {
        RecomendationInfoApi().getRecomendationInfo(context, fsqId, onFinish)
    }
}