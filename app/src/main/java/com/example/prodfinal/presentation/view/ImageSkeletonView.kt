package com.example.prodfinal.presentation.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import com.example.prodfinal.R

// Скелетон фотограции для экрана с информацией о рекомендации

@Composable
fun ImageSkeletonView(modifier: Modifier) {
    val transition = rememberInfiniteTransition("")

    // Анимация для градиента
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 2500f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 8000, easing = LinearEasing),
            RepeatMode.Restart
        ),
        label = "sceleton",
    )

    val shimmerColors = listOf(
        colorResource(id = R.color.sceleton1),
        colorResource(id = R.color.sceleton2),
    )

    // Градиент
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation.value, translateAnimation.value),
        end = Offset(translateAnimation.value + 300f, translateAnimation.value + 300f),
        tileMode = TileMode.Mirror,
    )

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        )
    }
}