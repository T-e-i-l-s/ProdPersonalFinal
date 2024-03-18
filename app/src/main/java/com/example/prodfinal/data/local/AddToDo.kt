package com.example.prodfinal.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.prodfinal.domain.model.ToDoItemModel
import com.google.gson.Gson

class AddToDo {
    fun addToDo(
        context: Context,
        newItem: ToDoItemModel
    ) {
        val list = GetToDoArray().getToDo(context)

        list.add(newItem)
        val gson = Gson()
        val jsonList = gson.toJson(list)

        val sharedPreferences = context.getSharedPreferences(
            "LifestyleHUB",
            Context.MODE_PRIVATE
        )

        sharedPreferences.edit()
            .putString("todo_list", jsonList)
            .apply()
    }
}