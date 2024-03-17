package com.example.prodfinal.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class TabBarItemModel(
    val title: String,
    val icon: Painter,
    val route: String
)
