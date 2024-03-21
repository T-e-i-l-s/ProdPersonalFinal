package com.example.prodfinal.data.local

import android.content.Context
import com.example.prodfinal.domain.model.UserModel
import com.google.gson.Gson

// Класс для сохранения данных об авторизованном пользоваетеле

class SaveCurrentUser {
    // Функция, которая переводит данные о пользователе в json и сохраняет в SharedPreferences
    fun saveCurrentUser (context: Context, user: UserModel) {
        // Переводим в json
        val gson = Gson()
        val userJson = gson.toJson(user)

        // Сохраняем в SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("current_user", userJson)
            .putBoolean("is_registered", true)
            .apply()
    }
}