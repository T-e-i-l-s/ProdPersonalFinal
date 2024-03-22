package com.example.prodfinal.domain.authorization

import android.content.Context
import com.example.prodfinal.data.source.CurrentUserSource
import com.example.prodfinal.domain.model.UserModel
import com.google.gson.Gson
import org.json.JSONArray

// Класс для работы с авторизацией

class Authorization {
    // Функция, которая проверяет корректность введенных данных
    fun checkAccess(
        context: Context,
        username: String,
        password: String,
        onFinish: (result: Boolean) -> Unit
    ) {
        // Получаем список пользователей
        Authorization().getUsers(context) { usersList ->
            // Проходим по всем юзерам и проверяем есть ли совпадения с введенными данными
            for (i in 0..<usersList.size) {
                if (username == usersList[i].name && password == usersList[i].password) {
                    CurrentUserSource().saveCurrentUser(context, usersList[i])

                    val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
                    sharedPref.edit()
                        .putBoolean("is_registered", true)
                        .apply()

                    onFinish(true)

                    return@getUsers
                }
            }
            onFinish(false)
        }
    }

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