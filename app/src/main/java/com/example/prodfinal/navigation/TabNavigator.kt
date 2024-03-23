package com.example.prodfinal.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.TabBarItemModel
import com.example.prodfinal.presentation.screen.BudgetScreen
import com.example.prodfinal.presentation.screen.MainScreen
import com.example.prodfinal.presentation.screen.ToDoScreen
import com.example.prodfinal.presentation.screen.UserInfoScreen

// Индекс открытой страницы в массиве
var selectedItem = mutableIntStateOf(0)
// Route открытой страницы
var currentRoute = mutableStateOf("main_screen")

// Граф Tab навигации(между графом с Tab навигацией и вспомогательными экранами)
@Composable
fun TabNavigator(stackNavigator: NavController) {
    // Все страницы в таб-навигации
    val tabBarItems = listOf(
        TabBarItemModel(
            title = "Главная",
            icon = painterResource(R.drawable.home_icon),
            "main_screen"
        ),
        TabBarItemModel(
            title = "Мой досуг",
            icon = painterResource(R.drawable.list_icon),
            "todo_screen"
        ),
        TabBarItemModel(
            title = "Бюджет",
            icon = painterResource(R.drawable.budget_icon),
            "budget_screen"
        ),
        TabBarItemModel(
            title = "Профиль",
            icon = painterResource(R.drawable.user_icon),
            "user_info_screen"
        )
    )

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(id = R.color.background),
            ) {
                TabView(navController = navController, tabBarItems)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    innerPadding.calculateBottomPadding()
                )
                .background(colorResource(id = R.color.background))
        ) {
            Navigations(navController = navController, stackNavigator)
        }
    }
}

// Таб бар
@Composable
fun TabView(navController: NavController, tabs: List<TabBarItemModel>) {
    NavigationBar(
        contentColor = Color.Transparent,
        containerColor = colorResource(id = R.color.background)
    ) {
        tabs.forEachIndexed { index, item ->
            NavigationBarItem(
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
                label = {
                    Text(
                        text = item.title,
                        fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                    )
                },
                selected = selectedItem.intValue == index,
                onClick = {
                    selectedItem.intValue = index
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
fun Navigations(navController: NavHostController, stackNavigator: NavController) {
    NavHost(navController, startDestination = currentRoute.value) {
        composable("main_screen") {
            ChangeStatusBarColor(colorResource(id = R.color.weather))
            MainScreen(LocalContext.current, stackNavigator)
        }
        composable("todo_screen") {
            ChangeStatusBarColor(colorResource(id = R.color.background))
            ToDoScreen(LocalContext.current, stackNavigator)
        }
        composable("budget_screen") {
            ChangeStatusBarColor(colorResource(id = R.color.background))
            BudgetScreen(LocalContext.current, stackNavigator)
        }
        composable("user_info_screen") {
            ChangeStatusBarColor(colorResource(id = R.color.background))
            UserInfoScreen(LocalContext.current, stackNavigator)
        }
    }
}
