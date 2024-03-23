package com.example.prodfinal.data.source

import android.content.Context
import com.example.prodfinal.domain.model.ToDoItemModel
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
        // Получаем список дел через класс GetToDoList
        val list = getToDo(context)

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

    /*
    Функция, которая получает json списка дел и
    переводит его в ArrayList
    */
    private fun decryptJson(jsonList: String): ArrayList<ToDoItemModel> {
        val list = ArrayList<ToDoItemModel>() // Создаем новый ArrayList
        val jsonArray = JSONArray(jsonList) // Получаем JSONArray из сроки
        // Проходим по всем элементам jsonArray и переводим их в ArrayList
        for (i in 0..<jsonArray.length()) {
            // Парсим элемент массива
            val element = jsonArray.getJSONObject(i)
            // Переводим в ToDoItemModel
            val item = ToDoItemModel(
                element.getString("mode"),
                element.getString("name"),
                element.getString("description"),
                element.getString("date"),
                element.getString("placeId"),
                element.getString("placeName"),
            )
            // Добавляем полученный ToDoItemModel в ArrayList
            list.add(item)
        }
        // Возвращаем ArrayList
        return list
    }
}