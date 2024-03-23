package com.example.prodfinal.data.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.domain.model.UserModel
import org.json.JSONObject

class RandomUserApi {
    // Функция,которая получает погоду и обрабатывает ее
    fun getRandomUser(
        context: Context,
        onFinish: (result: UserModel) -> Unit
    ) {

        // URL запроса
        val url = "https://randomuser.me/api/"

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
            {
                // Обрабатываем ошибку(возвращаем "неудачный" ответ)
            }
        )

        // Добавляем запрос в очередь
        requestQueue.add(stringRequest)
    }

    // Функция, которая обрабатывает json
    private fun handleResponse(response: String, onFinish: (response: UserModel) -> Unit) {
        // Парсим все необходимое
        val json = JSONObject(response).getJSONArray("results").getJSONObject(0)

        val loginInfo = json.getJSONObject("login")
        val password = loginInfo.getString("password")
        val name = loginInfo.getString("username")

        val mail = json.getString("email")
        val phoneNum = json.getString("phone")

        val birthdayObj = json.getJSONObject("dob")
        val birthday = birthdayObj.getString("date")

        val location = json.getJSONObject("location")
        val street = location.getJSONObject("street")
        val adress = "" + street.get("name") + " " + street.get("number")

        // Отдаем
        onFinish(
            UserModel(
                name,
                mail,
                birthday,
                adress,
                phoneNum,
                password
            )
        )
    }
}