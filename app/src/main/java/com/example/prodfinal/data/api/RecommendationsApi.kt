package com.example.prodfinal.data.api

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.domain.model.RecomendationModel
import org.json.JSONObject

// Класс для получения рекомендаций(мест рядом)

class RecommendationsApi {
    // Функция, которая делает запрос к апи
    fun getRecommendations(
        context: Context,
        lat: Double,
        lon: Double,
        onFinish: (MutableList<RecomendationModel>) -> Unit
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
            { error ->
                // Обрабатываем ошибку(возвращаем "неудачный" ответ)
                Log.e("RECOMENDATION_API_ERROR", error.toString())
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

    // Функция, которая обрабатывает json всех локаций в mutableListOf<RecomendationModel>
    fun handleResponse(
        response: String,
        onFinish: (MutableList<RecomendationModel>) -> Unit
    ) {
        // Создаем новый список
        val recomendations = mutableListOf<RecomendationModel>()
        // Получаем json из ответа апи
        val json = JSONObject(response)
        // Получаем json массив мест
        val recomendationsArray = json.getJSONArray("results")
        // Проходим по всем элементам jsonArray и переводим их в ArrayList
        for (i in 0..<recomendationsArray.length()) {
            // Получаем json одной из локаций и обрабатываем
            val recomendation: RecomendationModel =
                handleRecomendationJson(recomendationsArray.getJSONObject(i))
            // Добавляем элемент к списку
            recomendations.add(recomendation)
        }
        // Возвращаем список мест
        onFinish(recomendations)
    }

    // Функция, которая обрабатывает json локации в RecomendationModel
    private fun handleRecomendationJson(
        response: JSONObject
    ): RecomendationModel {
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

        return RecomendationModel(
            id,
            title,
            image,
            address,
            categories
        )
    }
}