package com.example.prodfinal.data.source

import android.content.Context
import com.example.prodfinal.domain.model.FullRecomendationModel
import com.example.prodfinal.domain.model.RecomendationModel
import com.example.prodfinal.domain.model.UserModel
import com.google.gson.Gson
import org.json.JSONObject

class RecomendationSource {
    fun isSaved(
        context: Context,
        id: String
    ): Boolean {
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        return sharedPref.contains(id)
    }

    fun saveRecomendationData(
        context: Context,
        recomendation: FullRecomendationModel
    ) {
        // Переводим в json
        val gson = Gson()
        val recomendationJson = gson.toJson(recomendation)

        // Сохраняем в SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString(recomendation.id, recomendationJson)
            .apply()
    }

    fun getRecomendationData(
        context: Context,
        id: String
    ): FullRecomendationModel {
        // Получаем данные о пользователе из SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        val recomendationString = sharedPref.getString(id, "")

        // Переводим данные о пользователе в json и отдаем
        val recomendationJson = JSONObject(recomendationString)

        val photosJson = recomendationJson.getJSONArray("photos")
        val photos = ArrayList<String>()
        for (i in 0..<photosJson.length()) {
            photos.add(photosJson[i].toString())
        }

        val categoriesJson = recomendationJson.getJSONArray("category")
        val categories = ArrayList<String>()
        for (i in 0..<categoriesJson.length()) {
            categories.add(categoriesJson[i].toString())
        }

        var mail: String? = null
        if (recomendationJson.has("mail")) {
            mail = recomendationJson.getString("mail")
        }

        return FullRecomendationModel(
            recomendationJson.getString("id"),
            recomendationJson.getString("title"),
            photos,
            recomendationJson.getString("address"),
            categories,
            mail,
        )
    }
}