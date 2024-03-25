package com.example.prodfinal.data.source

import android.content.Context
import com.example.prodfinal.domain.model.UserModel
import com.google.gson.Gson
import org.json.JSONObject

// Класс работы с авторизованным пользователем

class CurrentUserSource {
    // Функция, которая получает зарегестрирован ли пользователь из SharedPreferences
    fun isRegistered(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("is_registered", false)
    }

    // Функция, которая получает данные о пользователе из SharedPreferences
    fun getCurrentUser(context: Context): UserModel {
        // Получаем данные о пользователе из SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        val user = sharedPref.getString("current_user", "") ?:
            return UserModel("", "", "", "", "", "", "")

        val json = JSONObject(user)
        return UserModel(
            json.getString("username"),
            json.getString("photo"),
            json.getString("mail"),
            json.getString("birthday"),
            json.getString("address"),
            json.getString("phoneNumber"),
            json.getString("password"),
        )
    }

    // Функция, которая удаляет из SharedPreferences данные о пользователе
    fun logOut(context: Context) {
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        sharedPref.edit().remove("current_user").remove("is_registered").apply()
    }

    // Функция, которая переводит данные о пользователе в json и сохраняет в SharedPreferences
    fun saveCurrentUser(context: Context, user: UserModel) {
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