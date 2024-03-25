package com.example.prodfinal.data.source

import android.content.Context
import com.example.prodfinal.domain.model.FullRecommendationModel
import com.google.gson.Gson
import org.json.JSONObject

// Класс работы с информацией о рекомендациях пользователем

class RecomendationSource {
    // Функция, которая проверяет, сохранены ли данные о локации в SharedPreferences
    fun isSaved(
        context: Context,
        id: String
    ): Boolean {
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        return sharedPref.contains(id)
    }

    // Функция, которая сохраняет данные о локации в SharedPreferences
    fun saveRecomendationData(
        context: Context,
        recomendation: FullRecommendationModel
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

    // Функция, которая получает данные о локации из SharedPreferences
    fun getRecomendationData(
        context: Context,
        id: String
    ): FullRecommendationModel {
        // Получаем данные о локации из SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        val recomendationString = sharedPref.getString(id, "")
            ?: return FullRecommendationModel("", "", arrayListOf(), "", arrayListOf(), "")

        // Переводим данные о локации в FullRecomendationModel и отдаем
        return decodeRecomendationJson(recomendationString)
    }

    // Функция, которая переводит json строку в FullRecomendationModel
    private fun decodeRecomendationJson(recomendationString: String): FullRecommendationModel {
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

        return FullRecommendationModel(
            recomendationJson.getString("id"),
            recomendationJson.getString("title"),
            photos,
            recomendationJson.getString("address"),
            categories,
            mail,
        )
    }
}