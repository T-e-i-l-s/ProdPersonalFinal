package com.example.prodfinal.data.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.domain.model.FullRecomendationModel
import com.example.prodfinal.domain.model.RecomendationModel
import org.json.JSONObject

class RecomendationInfoApi {
    fun getRecomendationInfo(
        context: Context,
        fsqId: String,
        onFinish: (FullRecomendationModel) -> Unit
    ) {

        // URL запроса
        val url = "https://api.foursquare.com/v3/places/$fsqId?fields=fsq_id%2Cname%2Cphotos%2Clocation%2Ccategories%2Cemail"

        // Создаем очередь для запросов
        val requestQueue = Volley.newRequestQueue(context)

        // Создаем запрос
        val stringRequest = object : StringRequest(
            Request.Method.GET,
            url,
            { response ->
                // Обрабатываем его и отдаем виджету
                handleResponse(JSONObject(response), onFinish)
            },
            { error ->
                // Обрабатываем ошибку(возвращаем "неудачный" ответ)
                Log.e("RECOMENDATION_INFO_API_ERROR", "ERROR")
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

    // Функция, которая обрабатывает json локации в RecomendationModel
    fun handleResponse(
        response: JSONObject,
        onFinish: (FullRecomendationModel) -> Unit
    ) {
        val id = response.getString("fsq_id")
        val title = response.getString("name")
        var email: String? = null
        if (response.has("email")) {
            email = response.getString("email")
        }

        val categories = ArrayList<String>()
        val categoryArray = response.getJSONArray("categories")
        for (i in 0..categoryArray.length()-1) {
            val category = categoryArray.getJSONObject(i)
            categories.add(category.getString("short_name"))
        }

        val images = ArrayList<String>()
        val imagesArray = response.getJSONArray("photos")
        for (i in 0..imagesArray.length()-1) {
            val image = imagesArray.getJSONObject(i)
            images.add(
                image.getString("prefix") + "original" + image.getString("suffix")
            )
        }

        val locationObj = response.getJSONObject("location")
        val address = locationObj.getString("formatted_address")

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