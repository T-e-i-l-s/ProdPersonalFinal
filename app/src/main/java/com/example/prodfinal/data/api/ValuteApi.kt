package com.example.prodfinal.data.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.data.mapper.ValuteMapper
import com.example.prodfinal.domain.model.ValuteExchangeModel
import com.example.prodfinal.domain.state.ValuteRate
import org.json.JSONObject

// Класс для получения курса валют

class ValuteApi {
    // Функция, которая делает запрос к api
    fun getValuteExchange(
        context: Context,
        onFinish: (result: ValuteExchangeModel) -> Unit
    ) {
        // URL запроса
        val url = "https://www.cbr-xml-daily.ru/daily_json.js"

        // Создаем очередь для запросов
        val requestQueue = Volley.newRequestQueue(context)

        // Создаем запрос
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                // Обрабатываем его и отдаем виджету
                handleResponse(response, onFinish)
            },
            {}
        )

        // Добавляем запрос в очередь
        requestQueue.add(stringRequest)
    }

    // Функция, которая обрабатывает результат и отдает виджету
    private fun handleResponse(
        response: String,
        onFinish: (result: ValuteExchangeModel) -> Unit
    ) {
        // Парсим все необходимое
        val json = JSONObject(response)
        val valute = json.getJSONObject("Valute")
        val usd = valute.getJSONObject("USD")
        val eur = valute.getJSONObject("EUR")

        // Отдаем виджету
        onFinish(
            ValuteMapper().invoke(
                ValuteExchangeModel(
                    usd.getString("Value").toBigDecimal(),
                    usd.getString("Previous").toBigDecimal(),
                    ValuteRate.FALL,
                    eur.getString("Value").toBigDecimal(),
                    eur.getString("Previous").toBigDecimal(),
                    ValuteRate.FALL,
                )
            )
        )
    }
}