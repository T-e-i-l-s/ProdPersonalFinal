package com.example.prodfinal.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

// Функция, которая меняет цвет статус бара
@Composable
fun ChangeStatusBarColor(color: Color) {
    val activity = LocalContext.current as Activity
    activity.window.statusBarColor = color.toArgb()
}
