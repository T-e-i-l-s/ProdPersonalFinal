package com.example.prodfinal.data.local

import android.content.Context

// Класс для выхода из аккаунта

class LogOut {
    // Функция, которая удаляем из SharedPreferences данные о пользователе
    fun logOut (context: Context) {
        val sharedPref = context.getSharedPreferences("LifestyleHUB",Context.MODE_PRIVATE)
        sharedPref.edit().remove("current_user").remove("is_registered").apply()
    }
}