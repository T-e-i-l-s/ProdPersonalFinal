package com.example.prodfinal.data.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.domain.model.RecomendationModel
import com.example.prodfinal.domain.model.WeatherModel
import org.json.JSONObject

class RecomendationApi {
    fun getRecomendations (
        context: Context,
        lat: Double,
        lon: Double,
        onFinish: (MutableList<RecomendationModel>) -> Unit
    ) {

        // URL запроса
        val url = "https://api.foursquare.com/v3/places/nearby?fields=name%2Cphotos%2Ccategories%2Clocation%2Cfsq_id&ll=$lat%2C$lon"

        // Создаем очередь для запросов
        val requestQueue = Volley.newRequestQueue(context)

        // Создаем запрос
        val stringRequest = object : StringRequest(
            Request.Method.GET,
            url,
            { response ->
            Log.e("RECOMENDATIONS123",response)
                // Обрабатываем его и отдаем виджету
                handleResponse(response, onFinish)
            },
            { error ->
                // Обрабатываем ошибку(возвращаем "неудачный" ответ)
                Log.e("RECOMENDATION_API_ERROR", "ERROR")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                var headers = HashMap<String, String>()
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
        val json = JSONObject(response)
        val recomendationsArray = json.getJSONArray("results")
        val recomendations = mutableListOf<RecomendationModel>()
        for(i in 0..recomendationsArray.length()-1) {
            val recomendation: RecomendationModel =
                handleRecomendationJson(recomendationsArray.getJSONObject(i))
            recomendations.add(recomendation)
        }
        onFinish(recomendations)
    }

    // Функция, которая обрабатывает json локации в RecomendationModel
    fun handleRecomendationJson(
        response: JSONObject
    ) : RecomendationModel {
        val id = response.getString("fsq_id")
        val title = response.getString("name")

        val categories = ArrayList<String>()
        val categoryArray = response.getJSONArray("categories")
        for(i in 0..categoryArray.length()-1) {
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