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


        onFinish(
            mutableListOf(
                RecomendationModel(
                    "Аптека",
                    "https://sbsmegamall.ru/upload/resize_cache/iblock/428/700_500_0/428b73c4787fdc5a80f97f35c5ae8c67.jpg",
                    "Губкина 30б",
                    "здоровье",
                ),
                RecomendationModel(
                    "Пятерочка",
                    "https://s0.rbk.ru/v6_top_pics/media/img/5/00/756625361058005.webp",
                    "Губкина 30г",
                    "продукты",
                ),
                RecomendationModel(
                    "Школа",
                    "https://upload.wikimedia.org/wikipedia/commons/a/ae/295%3D2019.06.10%3D%D0%9C%D0%B5%D0%B6%D0%B4%D1%83%D0%BD%D0%B0%D1%80%D0%BE%D0%B4%D0%BD%D0%B0%D1%8F_%D1%88%D0%BA%D0%BE%D0%BB%D0%B0_%D0%9A%D0%B0%D0%B7%D0%B0%D0%BD%D0%B8%3DDSC_0263.JPG",
                    "Губкина 30а",
                    "образование",
                ),
            )
        )

//        // Ключ от api
//        val apiKey = "97f99a723034917ca5ad6313b64249db"
//        // URL запроса
//        val url = "https://api.openweathermap.org/data/2.5/weather?" +
//                "lat=$lat" +
//                "&lon=$lon" +
//                "&appid=$apiKey" +
//                "&lang=ru" +
//                "&units=metric"
//
//        // Создаем очередь для запросов
//        val requestQueue = Volley.newRequestQueue(context)
//
//        // Создаем запрос
//        val stringRequest = StringRequest(
//            Request.Method.GET,
//            url,
//            { response ->
//                // Обрабатываем его и отдаем виджету
////                handleResponse(response, onFinish)
//            },
//            { error ->
//                // Обрабатываем ошибку(возвращаем "неудачный" ответ)
//                Log.e("RECOMENDATION_API_ERROR", "ERROR")
//            }
//        )
//
//        // Добавляем запрос в очередь
//        requestQueue.add(stringRequest)
    }


    // Функция, которая обрабатывает json погоды в WeatherItem
    fun handleResponse(
        response: String,
        onFinish: (List<RecomendationModel>) -> Unit
    ) {
        val json = JSONObject(response)
    }
}