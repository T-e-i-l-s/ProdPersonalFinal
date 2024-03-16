package com.example.prodfinal.domain.model

import android.media.Image
import androidx.compose.ui.graphics.painter.Painter

data class TabBarItem(
    val title: String,
    val icon: Painter,
    val route: String
)
