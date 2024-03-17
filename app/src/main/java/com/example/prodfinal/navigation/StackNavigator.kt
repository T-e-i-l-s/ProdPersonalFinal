package com.example.prodfinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodfinal.presentation.screen.AuthorisationScreen

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
    }
}