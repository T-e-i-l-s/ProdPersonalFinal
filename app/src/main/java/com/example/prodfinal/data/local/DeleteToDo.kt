package com.example.prodfinal.data.local

import android.content.Context
import com.example.prodfinal.domain.model.ToDoItemModel
import com.google.gson.Gson

// Класс для удаления элемента из списка дел

class DeleteToDo {
    /*
    Функция, которая удаляет элемент из списка дел и
    сохраняет изменения в SharedPreferences
    */
    fun deleteToDo(
        context: Context,
        index: Int
    ): ArrayList<ToDoItemModel> {
        // Получаем список дел через класс GetToDoList
        val list = GetToDoList().getToDo(context)

        // Удаляем элемент из списка
        list.removeAt(index)

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

        // Возвращаем обновленный список
        return list
    }
}