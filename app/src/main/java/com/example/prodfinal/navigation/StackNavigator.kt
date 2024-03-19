package com.example.prodfinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodfinal.presentation.screen.AuthorisationScreen
import com.example.prodfinal.presentation.screen.CreateToDoScreen
import com.example.prodfinal.presentation.screen.RecomendationScreen

// Граф Stack навигации(между графом с Tab навигацией и вспомогательными экранами)

@Composable
fun StackNavigator () {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_component"
    ) {
        // Граф Tab навигации
        composable(
            "main_component"
        ) {
            TabNavigator(navController)
        }

        // Экран авторизации
        composable(
            "authorization_screen/{mode}"
        ) {
            AuthorisationScreen(LocalContext.current, navController)
        }

        // Экран подробностей о рекомендации(месте)
        composable(
            "recomendation_screen/{fsq_id}"
        ) {
            RecomendationScreen(LocalContext.current, navController)
        }

        // Экран создания нового элемента todo
        composable(
            "create_todo_screen/{mode}/{place_id}/{place_name}"
        ) {
            CreateToDoScreen(LocalContext.current, navController)
        }
    }
}