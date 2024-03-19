package com.example.prodfinal.data.local

import android.content.Context
import com.example.prodfinal.domain.model.ToDoItemModel
import com.google.gson.Gson

// Класс для добавления нового элемента в список дел

class AddToDo {
    /*
    Функция, которая добавляет новый элемент к списку дел и
    сохраняет изменения в SharedPreferences
    */
    fun addToDo(
        context: Context,
        newItem: ToDoItemModel
    ) {
        // Получаем список дел через класс GetToDoList
        val list = GetToDoList().getToDo(context)

        // Добавляем новый элемент в список
        list.add(newItem)

        // Получаем json обновленного списка дел
        val gson = Gson()
        val jsonList = gson.toJson(list)

        // Сохраняем изменения в SharedPreferences
        val sharedPreferences = context.getSharedPreferences(
            "LifestyleHUB",
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit()
            .putString("todo_list", jsonList)
            .apply()
    }
}