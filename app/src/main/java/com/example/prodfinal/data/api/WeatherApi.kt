package com.example.prodfinal.data.api

import android.content.Context
import com.example.prodfinal.domain.model.WeatherModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.data.mapper.WeatherMapper
import org.json.JSONObject

// Класс для получения погоды на lat и lon

class WeatherApi {
    // Функция,которая получает погоду и обрабатывает ее
    fun getWeather(
        context: Context,
        lat: Double,
        lon: Double,
        onFinish: (result: WeatherModel) -> Unit
    ) {
        // Ключ от openweathermap
        val apiKey = "97f99a723034917ca5ad6313b64249db"
        // URL запроса
        val url = "https://api.openweathermap.org/data/2.5/weather?" +
                "lat=$lat" +
                "&lon=$lon" +
                "&appid=$apiKey" +
                "&lang=ru" +
                "&units=metric"

        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                handleResponse(response, onFinish)
            },
            {}
        )

        requestQueue.add(stringRequest)
    }

    // Функция, которая обрабатывает json погоды в WeatherItem
    private fun handleResponse(response: String, onFinish: (response: WeatherModel) -> Unit) {
        val json = JSONObject(response)

        val mainInfo = json.getJSONObject("main")

        val weatherInfo = json
            .getJSONArray("weather")
            .getJSONObject(0)

        val weatherName = weatherInfo
            .getString("description")
            .replaceFirstChar(Char::titlecase)

        val weatherIcon = weatherInfo.getString("icon")

        val city = json.getString("name")

        onFinish(
            WeatherMapper().invoke(
                WeatherModel(
                    city,
                    weatherName,
                    weatherIcon,
                    mainInfo.getString("temp"),
                    mainInfo.getString("temp_min"),
                    mainInfo.getString("temp_max"),
                    mainInfo.getString("feels_like"),
                )
            )
        )
    }
}