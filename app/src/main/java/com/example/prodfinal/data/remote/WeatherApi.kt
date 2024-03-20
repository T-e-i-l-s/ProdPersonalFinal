package com.example.prodfinal.data.remote

import android.content.Context
import com.example.prodfinal.domain.model.WeatherModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.math.roundToInt

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
            { error ->
                // Обрабатываем ошибку(возвращаем "неудачный" ответ)
                onFinish(
                    WeatherModel(
                        "ERROR",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                    )
                )
            }
        )

        // Добавляем запрос в очередь
        requestQueue.add(stringRequest)
    }

    // Функция, которая обрабатывает json погоды в WeatherItem
    fun handleResponse(response: String, onFinish: (response: WeatherModel) -> Unit) {
        val json = JSONObject(response)
        // Основные параметры(температура и т.д.)
        val main_info = json.getJSONObject("main")
        // Информация о погоде(название, иконка и т.д.)
        val weatherInfo = json
            .getJSONArray("weather")
            .getJSONObject(0)
        // Название погоды(ясно и т.д.)
        val weatherName = weatherInfo
            .getString("description")
            .capitalize()
        // Иконка погоды в формате https://openweathermap.org/weather-conditions
        val weatherIcon = weatherInfo
            .getString("icon")
        // Город
        val city = json
            .getString("name")

        // Отдаем погоду виджету
        onFinish(
            WeatherModel(
                city,
                weatherName,
                weatherIcon,
                "" + main_info.getDouble("temp").roundToInt(),
                "" + main_info.getDouble("temp_min").roundToInt(),
                "" + main_info.getDouble("temp_max").roundToInt(),
                "" + main_info.getDouble("feels_like").roundToInt(),
            )
        )
    }
}