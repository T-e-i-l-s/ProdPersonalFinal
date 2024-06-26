package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.api.ValuteApi
import com.example.prodfinal.domain.model.ValuteExchangeModel

// Класс репозитория для ValuteExchangeApi

class ValuteRepositoryImpl {
    fun getValuteExchange(
        context: Context,
        onFinish: (result: ValuteExchangeModel) -> Unit
    ) {
        ValuteApi().getValuteExchange(context, onFinish)
    }
}