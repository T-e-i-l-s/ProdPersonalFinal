package com.example.prodfinal.data.api

import android.content.Context
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.domain.model.ShortRecommendationModel
import org.json.JSONObject

// Класс для получения рекомендаций(мест рядом)

class RecommendationsApi {
    // Функция, которая делает запрос к апи
    fun getRecommendations(
        context: Context,
        lat: Double,
        lon: Double,
        onFinish: (MutableList<ShortRecommendationModel>) -> Unit
    ) {
        // URL запроса
        val url = "https://api.foursquare.com/v3/places/nearby?fields=" +
                "name%2" +
                "Cphotos%2" +
                "Ccategories%2" +
                "Clocation%2" +
                "Cfsq_id&ll=$lat%2C$lon"

        // Создаем очередь для запросов
        val requestQueue = Volley.newRequestQueue(context)

        // Создаем запрос
        val stringRequest = object : StringRequest(
            Method.GET,
            url,
            { response ->
                // Обрабатываем результат и отдаем
                handleResponse(response, onFinish)
            },
            {}
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

    // Функция, которая обрабатывает json всех локаций в mutableListOf<ShortRecommendationModel>
    fun handleResponse(
        response: String,
        onFinish: (MutableList<ShortRecommendationModel>) -> Unit
    ) {
        // Создаем новый список
        val recommendations = mutableListOf<ShortRecommendationModel>()
        // Получаем json из ответа апи
        val json = JSONObject(response)
        // Получаем json массив мест
        val recommendationsArray = json.getJSONArray("results")
        // Проходим по всем элементам jsonArray и переводим их в ArrayList
        for (i in 0..<recommendationsArray.length()) {
            // Получаем json одной из локаций и обрабатываем
            val recommendation: ShortRecommendationModel =
                handleRecommendationJson(recommendationsArray.getJSONObject(i))
            // Добавляем элемент к списку
            recommendations.add(recommendation)
        }
        // Возвращаем список мест
        onFinish(recommendations)
    }

    // Функция, которая обрабатывает json локации в ShortRecommendationModel
    private fun handleRecommendationJson(
        response: JSONObject
    ): ShortRecommendationModel {
        // Парсим все необходимое
        val id = response.getString("fsq_id")
        val title = response.getString("name")

        val categories = ArrayList<String>()
        val categoryArray = response.getJSONArray("categories")
        for (i in 0..<categoryArray.length()) {
            val category = categoryArray.getJSONObject(i)
            categories.add(category.getString("short_name"))
        }

        val imagesArr = response.getJSONArray("photos")
        var image = ""
        if (imagesArr.length() > 0) {
            val imageObj = imagesArr.getJSONObject(0)
            image = imageObj.getString("prefix") + "original" + imageObj.getString("suffix")
        }

        val locationObj = response.getJSONObject("location")
        val address = locationObj.getString("formatted_address")

        // Отдаем
        return ShortRecommendationModel(
            id,
            title,
            image,
            address,
            categories
        )
    }
}