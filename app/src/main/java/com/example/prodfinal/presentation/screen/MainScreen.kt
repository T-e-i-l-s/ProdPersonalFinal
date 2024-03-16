package com.example.prodfinal.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.WeatherItem
import com.example.prodfinal.presentation.view.WeatherView

@Composable
fun MainScreen () {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(10.dp)
    ) {
        WeatherView(weatherItem = WeatherItem(
            "Казань",
            "Солнечно",
            "1°",
            "-5°",
            "3°",
            "-2°",
        ))
    }
}