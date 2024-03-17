package com.example.prodfinal.domain.authorization

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.domain.model.UserModel
import org.json.JSONObject

class GetRandomUser {
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
            { error ->
                // Обрабатываем ошибку(возвращаем "неудачный" ответ)
                Log.e("AUTH_ERROR", error.toString())
            }
        )

        // Добавляем запрос в очередь
        requestQueue.add(stringRequest)
    }

    // Функция, которая обрабатывает json
    fun handleResponse(response: String, onFinish: (response: UserModel) -> Unit) {
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

        onFinish(
            UserModel(
                mutableStateOf(name),
                mutableStateOf(mail),
                mutableStateOf(birthday),
                mutableStateOf(adress),
                mutableStateOf(phoneNum),
                mutableStateOf(password)
            )
        )
    }
}