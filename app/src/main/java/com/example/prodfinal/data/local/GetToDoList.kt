package com.example.prodfinal.data.local

import android.content.Context
import com.example.prodfinal.domain.model.ToDoItemModel
import org.json.JSONArray

// Класс для получения списка дел

class GetToDoList {
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