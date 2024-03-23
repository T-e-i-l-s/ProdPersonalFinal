package com.example.prodfinal.data.api

import android.content.Context
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.domain.model.FullRecomendationModel
import org.json.JSONObject

// Класс для получения полной информации о месте по fsq_id

class RecommendationInfoApi {
    // Функция, которая делает запрос к апи
    fun getRecommendationInfo(
        context: Context,
        fsqId: String,
        onFinish: (FullRecomendationModel) -> Unit
    ) {
        // URL запроса
        val url = "https://api.foursquare.com/v3/places/$fsqId?fields=" +
                "fsq_id%2C" +
                "name%2C" +
                "photos%2C" +
                "location%2C" +
                "categories%2C" +
                "email"

        // Создаем очередь для запросов
        val requestQueue = Volley.newRequestQueue(context)

        // Создаем запрос
        val stringRequest = object : StringRequest(
            Method.GET,
            url,
            { response ->
                // Обрабатываем результат и отдаем
                handleResponse(JSONObject(response), onFinish)
            },
            { error ->
                // Обрабатываем ошибку(возвращаем "неудачный" ответ)
            }
        ) {
            // Добавляем хедеры к запросу
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["accept"] = "application/json"
                headers["Authorization"] = "fsq3+2kGsGSBxL42Wxh4dNRb1ZixdUyTVPcDxi77c2By8f8="
                return headers
            }
        }

        // Добавляем запрос в очередь
        requestQueue.add(stringRequest)
    }

    // Функция, которая парсит все необходимые данные и возвращает как FullRecomendationModel
    fun handleResponse(
        response: JSONObject,
        onFinish: (FullRecomendationModel) -> Unit
    ) {
        // Парсим все необходимое
        val id = response.getString("fsq_id")
        val title = response.getString("name")
        var email: String? = null
        if (response.has("email")) {
            email = response.getString("email")
        }

        val categories = ArrayList<String>()
        val categoryArray = response.getJSONArray("categories")
        for (i in 0..<categoryArray.length()) {
            val category = categoryArray.getJSONObject(i)
            categories.add(category.getString("short_name"))
        }

        val images = ArrayList<String>()
        val imagesArray = response.getJSONArray("photos")
        for (i in 0..<imagesArray.length()) {
            val image = imagesArray.getJSONObject(i)
            images.add(
                image.getString("prefix") + "original" + image.getString("suffix")
            )
        }

        val locationObj = response.getJSONObject("location")
        val address = locationObj.getString("formatted_address")

        // Отдаем
        onFinish(
            FullRecomendationModel(
                id,
                title,
                images,
                address,
                categories,
                email,
            )
        )
    }
}