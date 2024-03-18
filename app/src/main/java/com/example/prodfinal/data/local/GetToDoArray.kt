package com.example.prodfinal.data.local

import android.content.Context
import android.util.Log
import com.example.prodfinal.domain.model.ToDoItemModel
import com.google.gson.Gson
import org.json.JSONArray

class GetToDoArray {
    fun getToDo(context: Context): ArrayList<ToDoItemModel> {
        val sharedPreferences = context.getSharedPreferences(
            "LifestyleHUB",
            Context.MODE_PRIVATE
        )
        val jsonList = sharedPreferences.getString(
            "todo_list",
            null
        )

        if (jsonList == null) {
            return ArrayList()
        }

        val jsonArray = JSONArray(jsonList)
        val list = ArrayList<ToDoItemModel>()
        for (i in 0..<jsonArray.length()) {
            val element = jsonArray.getJSONObject(i)
            val item = ToDoItemModel (
                element.getString("mode"),
                element.getString("name"),
                element.getString("description"),
                element.getString("date"),
                element.getString("placeId"),
            )
            list.add(item)
        }

        return list
    }
}