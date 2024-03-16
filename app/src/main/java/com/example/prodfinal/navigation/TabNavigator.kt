package com.example.prodfinal.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.TabBarItem
import com.example.prodfinal.presentation.screen.MainScreen
import com.example.prodfinal.presentation.screen.ToDoScreen
import com.example.prodfinal.presentation.screen.UserInfoScreen

@Composable
fun TabNavigator() {
    // Все страницы в таб-навигации
    val tabBarItems = listOf(
        TabBarItem(
            title = "Главная",
            icon = painterResource(R.drawable.home_icon),
            "main_screen"
        ),
        TabBarItem(
            title = "Мой досуг",
            icon = painterResource(R.drawable.list_icon),
            "todo_screen"
        ),
        TabBarItem(
            title = "Профиль",
            icon = painterResource(R.drawable.user_icon),
            "user_info_screen"
        )
    )

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier) {
                TabView(navController = navController, tabBarItems)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                0.dp,
                0.dp,
                0.dp,
                innerPadding.calculateBottomPadding()
            )
        ) {
            Navigations(navController = navController)
        }
    }
}

// Таб бар
@Composable
fun TabView (navController: NavController, tabs: List<TabBarItem>) {
    // Индекс открытой страницы в массиве
    var selectedItem = remember { mutableStateOf(0) }
    // Route открытой страницы
    var currentRoute = remember { mutableStateOf("main_screen") }

    NavigationBar (
        contentColor = Color.Transparent,
        containerColor = colorResource(id = R.color.background)
    ) {
        tabs.forEachIndexed { index, item ->
            NavigationBarItem (
                colors = NavigationBarItemDefaults.colors(
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.selected),
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                ),
                alwaysShowLabel = true,
                icon = { Icon(painter = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedItem.value == index,
                onClick = {
                    selectedItem.value = index
                    currentRoute.value = item.route
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Navigation graph
@Composable
fun Navigations(navController: NavHostController) {
    NavHost(navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen()
        }
        composable("todo_screen") {
            ToDoScreen()
        }
        composable("user_info_screen") {
            UserInfoScreen()
        }
    }
}
