package com.example.prodfinal.data.local

import android.content.Context
import com.example.prodfinal.domain.model.UserModel
import org.json.JSONObject

// Класс для получения и обработки данных о авторизованном пользователе

class GetCurrentUser {
    // Функция, которая получает зарегестрирован ли пользователь из SharedPreferences
    fun isRegistered(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("LifestyleHUB",Context.MODE_PRIVATE)
        return sharedPref.getBoolean("is_registered", false)
    }

    // Функция, которая получает данные о пользователя из SharedPreferences
    fun getCurrentUser(context: Context): UserModel {
        // Получаем данные о пользователе из SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB",Context.MODE_PRIVATE)
        val user = sharedPref.getString("current_user", "")

        // Переводим данные о пользователе в json и отдаем
        val json = JSONObject(user)
        return UserModel(
            json.getString("name"),
            json.getString("mail"),
            json.getString("birthday"),
            json.getString("address"),
            json.getString("phone_number"),
            json.getString("password"),
        )
    }
}