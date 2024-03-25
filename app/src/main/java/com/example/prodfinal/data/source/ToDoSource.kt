package com.example.prodfinal.data.source

import android.content.Context
import com.example.prodfinal.domain.model.ToDoItemModel
import com.example.prodfinal.domain.state.ToDoState
import com.google.gson.Gson
import org.json.JSONArray

// Класс работы с досугом

class ToDoSource {
    /*
    Функция, которая добавляет новый элемент к списку дел и
    сохраняет изменения в SharedPreferences
    */
    fun addToDo(
        context: Context,
        newItem: ToDoItemModel
    ) {
        val list = getToDo(context)

        list.add(newItem)

        saveToDoList(context, list)
    }

    // Функция, которая получает список дел и обрабатывает
    fun getToDo(context: Context): ArrayList<ToDoItemModel> {
        // Получаем данные из SharedPreferences
        val sharedPreferences = context.getSharedPreferences(
            "LifestyleHUB",
            Context.MODE_PRIVATE
        )
        val jsonList = sharedPreferences.getString(
            "todo_list",
            null
        ) ?: return ArrayList() // Проверяем не пустая ли строка

        // Возвращаем обработанный ArrayList
        return decryptJson(jsonList)
    }

    /*
    Функция, которая удаляет элемент из списка дел и
    сохраняет изменения в SharedPreferences
    */
    fun deleteToDo(
        context: Context,
        index: Int
    ): ArrayList<ToDoItemModel> {
        // Получаем список дел через класс GetToDoList
        val list = getToDo(context)

        if (index >= list.size) {
            return list
        }

        list.removeAt(index)

        saveToDoList(context, list)

        return list
    }

    /*
    Функция, которая получает json списка дел и
    переводит его в ArrayList
    */
    private fun decryptJson(jsonList: String): ArrayList<ToDoItemModel> {
        val list = ArrayList<ToDoItemModel>()
        val jsonArray = JSONArray(jsonList)
        // Проходим по всем элементам jsonArray и переводим их в ArrayList
        for (i in 0..<jsonArray.length()) {
            val element = jsonArray.getJSONObject(i)
            val item = ToDoItemModel(
                ToDoState.valueOf(element.getString("mode").uppercase()),
                element.getBoolean("isImportant"),
                element.getString("name"),
                element.getString("description"),
                element.getString("date"),
                element.getString("placeId"),
                element.getString("placeName"),
            )
            list.add(item)
        }
        return list
    }

    private fun saveToDoList(context: Context, list: ArrayList<ToDoItemModel>) {
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