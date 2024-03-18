package com.example.prodfinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodfinal.presentation.screen.AuthorisationScreen
import com.example.prodfinal.presentation.screen.CreateToDoScreen
import com.example.prodfinal.presentation.screen.RecomendationScreen

@Composable
fun StackNavigator () {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main_component"
    ) {
        composable(
            "main_component"
        ) {
            TabNavigator(navController)
        }
        composable(
            "authorization_screen/{mode}"
        ) {
            AuthorisationScreen(LocalContext.current, navController)
        }
        composable(
            "recomendation_screen/{fsq_id}"
        ) {
            RecomendationScreen(LocalContext.current, navController)
        }
        composable(
            "create_todo_screen/{mode}"
        ) {
            CreateToDoScreen(LocalContext.current, navController)
        }
    }
}