package com.example.prodfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.prodfinal.R

// Блок "Нет изображения" на случай отстуствия изображения в api

@Composable
fun NoPhotoView (modifier: Modifier) {
    Box(
        modifier = modifier
            .background(colorResource(id = R.color.no_image)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Нет изображения",
            fontSize = 19.sp,
            color = colorResource(id = R.color.text),
            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
        )
    }
}