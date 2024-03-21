package com.example.prodfinal.domain.authorization

import android.content.Context
import com.example.prodfinal.domain.model.UserModel
import com.google.gson.Gson
import org.json.JSONArray

// Класс для работы с авторизацией

class UserInfo {
    // Функция, которая создает нового пользователя
    fun createUser (context: Context, user: UserModel) {
        getUsers(context) { usersList ->
            // Добавляем нового пользователя
            usersList.add(user)

            // Переводим в json список авторизованных пользователей
            val gson = Gson()
            val users = gson.toJson(usersList)

            // Сохраняем SharedPreferences
            val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
            sharedPref.edit()
                .putString("users", users)
                .apply()
        }
    }

    // Функция, которая получает список пользователей
    fun getUsers (context: Context, onFinish: (result: ArrayList<UserModel>) -> Unit) {
        // Получаем данные из SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB",Context.MODE_PRIVATE)
        val data = sharedPref.getString("users", "")

        // Отсекаем случай с пустым списком
        if (data == "") {
            onFinish (
                ArrayList()
            )
            return
        }

        // Парсим все необходимые поля
        val json = JSONArray(data)
        val usersList = ArrayList<UserModel>()
        for (i in 0..<json.length()) {
            val user = json.getJSONObject(i)
            usersList.add(
                UserModel(
                    user.getString("name"),
                    user.getString("mail"),
                    user.getString("birthday"),
                    user.getString("address"),
                    user.getString("phone_number"),
                    user.getString("password"),
                )
            )
        }

        // Отдаем результат
        onFinish(usersList)
    }
}