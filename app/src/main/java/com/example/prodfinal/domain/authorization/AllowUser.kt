package com.example.prodfinal.domain.authorization

import android.content.Context
import com.example.prodfinal.data.local.SaveCurrentUser

// Класс для проверки пользователя(логин и пароль)

class AllowUser() {
    // Функция, которая проверяет корректность введенных данных
    fun checkAccess(
        context: Context,
        username: String,
        password: String
    ) {
        // Получаем список пользователей
        UserInfo().getUsers(context) { usersList ->
            // Проходим по всем юзерам и проверяем есть ли совпадения с введенными данными
            for (i in 0..<usersList.size) {
                if (username == usersList[i].name && password == usersList[i].password) {
                    SaveCurrentUser().saveCurrentUser(context, usersList[i])
                    val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
                    sharedPref.edit()
                        .putBoolean("is_registered", true)
                        .apply()
                }
            }
        }
    }
}