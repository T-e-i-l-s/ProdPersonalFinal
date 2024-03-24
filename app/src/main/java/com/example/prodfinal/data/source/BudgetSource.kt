package com.example.prodfinal.data.source

import android.content.Context
import com.example.prodfinal.domain.model.BudgetGoalModel
import com.google.gson.Gson
import org.json.JSONArray
import java.math.BigDecimal

// Класс работы с бюджетом

class BudgetSource {
    // Функция для увеличения полученной суммы у цели
    fun addToReceived (
        context: Context,
        index: Int,
        sum: String
    ): ArrayList<BudgetGoalModel> {
        // Получаем список целей
        val goals = getGoals(context)
        // Отсекаем случай с неверным index
        if (index >= goals.size) {
            return goals
        }

        // Цель, к которой нужно прибавить
        val goal = goals[index]

        // Число, которое нужно прибавить
        val numValue = sum.toBigDecimalOrNull() ?: return goals

        // Сохраняем изменения
        goals[index] = BudgetGoalModel(
            goal.name,
            goal.goal,
            goal.received.add(numValue)
        )

        if (numValue > goal.goal.subtract(goal.received)) { // Если полученная сумма > чем цель
            goals[index] = BudgetGoalModel(
                goal.name,
                goal.goal,
                goal.received.add(goal.goal.subtract(goal.received))
            )
        }

        // Сохраняем изменения
        saveGoals(context, goals)

        // Возвращаем обновленную сумму полученных/потраченных средств
        return goals
    }

    // Функция, которая удаляет цель
    fun deleteGoal (
        context: Context,
        index: Int
    ): ArrayList<BudgetGoalModel> {
        // Получаем список целей
        val goals = getGoals(context)
        // Отсекаем случай с неверным index
        if (index >= goals.size) {
            return goals
        }

        // Удаляем элемент
        goals.removeAt(index)

        // Сохраняем изменения
        saveGoals(context, goals)

        // Возвращаем обновленный список
        return goals
    }

    // Функция, которая создает цель
    fun createGoal (
        context: Context,
        goal: BudgetGoalModel,
    ) {
        // Получаем список целей
        val goals = getGoals(context)

        // Добавляем элемент
        goals.add(goal)

        // Сохраняем изменения
        saveGoals(context, goals)
    }

    // Функция, которая получает список целей
    fun getGoals (
        context: Context,
    ): ArrayList<BudgetGoalModel> {
        // Получаем json массива целей из SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        val goalsString = sharedPref.getString("goals", null) ?: return ArrayList()

        // Переводим массива целей в ArrayList<BudgetGoalModel> и отдаем
        val jsonArray = JSONArray(goalsString)
        val goals = ArrayList<BudgetGoalModel>()
        for(i in 0..<jsonArray.length()) {
            val goal = jsonArray.getJSONObject(i)
            goals.add(
                BudgetGoalModel(
                    goal.getString("name"),
                    BigDecimal(goal.getString("goal")),
                    BigDecimal(goal.getString("received")),
                )
            )
        }
        return goals
    }

    // Функция, которая сохраняет список целей
    private fun saveGoals (
        context: Context,
        goals: ArrayList<BudgetGoalModel>,
    ) {
        // Переводим в json
        val gson = Gson()
        val goalsJson = gson.toJson(goals)

        // Сохраняем в SharedPreferences
        val sharedPref = context.getSharedPreferences("LifestyleHUB", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("goals", goalsJson)
            .apply()
    }
}