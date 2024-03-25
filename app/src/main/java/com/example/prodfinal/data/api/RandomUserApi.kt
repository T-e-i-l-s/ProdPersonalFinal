package com.example.prodfinal.data.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodfinal.domain.model.UserModel
import org.json.JSONObject

// Класс для получения данных о рандомном пользователе

class RandomUserApi {
    // Функция,которая получает погоду и обрабатывает ее
    fun getRandomUser(
        context: Context,
        onFinish: (result: UserModel) -> Unit
    ) {
        // URL запроса
        val url = "https://randomuser.me/api/"

        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                // Обрабатываем его и отдаем виджету
                handleResponse(response, onFinish)
            },
            {}
        )

        requestQueue.add(stringRequest)
    }

    // Функция, которая обрабатывает json
    private fun handleResponse(response: String, onFinish: (response: UserModel) -> Unit) {
        val json = JSONObject(response).getJSONArray("results").getJSONObject(0)

        val loginInfo = json.getJSONObject("login")
        val password = loginInfo.getString("password")
        val username = loginInfo.getString("username")

        val mail = json.getString("email")
        val phoneNum = json.getString("phone")

        val birthdayObj = json.getJSONObject("dob")
        val birthday = birthdayObj.getString("date")

        val location = json.getJSONObject("location")
        val street = location.getJSONObject("street")
        val address = "" + street.get("name") + " " + street.get("number")

        val photoObj = json.getJSONObject("picture")
        val photo = photoObj.getString("medium")

        onFinish(
            UserModel(
                username,
                photo,
                mail,
                birthday,
                address,
                phoneNum,
                password
            )
        )
    }
}