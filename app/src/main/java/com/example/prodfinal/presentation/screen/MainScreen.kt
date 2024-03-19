package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.location.Location
import com.example.prodfinal.data.repository.RecomentadionRepositoryImpl
import com.example.prodfinal.data.repository.WeatherRepositoryImpl
import com.example.prodfinal.domain.model.RecomendationModel
import com.example.prodfinal.domain.model.WeatherModel
import com.example.prodfinal.presentation.view.RecomendationView
import com.example.prodfinal.presentation.view.WeatherView

// Были ли загруженны данные
var isDataLoaded = false

// Статус виджета погоды(загружается, нет доступа, загружен)
val weatherLoadingStatus = mutableStateOf("LOADING")

// Погода
val weatherInfo =
    mutableStateOf(
        WeatherModel(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        )
    )

// Список рекомендаций(места рядом)
var recomendations = mutableStateOf(mutableListOf<RecomendationModel>())

@Composable
fun MainScreen(context: Context, stackNavigator: NavController) {
    LaunchedEffect(!isDataLoaded) {
        // Получаем данные о геолокации
        Location(context).getLocation { locationResponse ->
            if (!locationResponse.isEnabled) { // Если не удалось получить геолокацию
                weatherLoadingStatus.value = "ERROR"
            } else { // Удалось получить геолокацию
                // Получаем погоду на данных координатах
                WeatherRepositoryImpl().getWeather(
                    context,
                    locationResponse.latitude,
                    locationResponse.longtitude
                ) { weatherResponse ->
                    weatherInfo.value = weatherResponse // Сохраняем данные о погоде
                    weatherLoadingStatus.value = "READY" // Меняем статус виджета на "загружен"
                }

                // Получаем рекомендации на данных координатах
                RecomentadionRepositoryImpl().getRecomendations(
                    context,
                    locationResponse.latitude,
                    locationResponse.longtitude
                ) { recomendationsResponse ->
                    recomendations.value = recomendationsResponse
                }
            }
        }
        isDataLoaded = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            WeatherView(
                weatherItem = weatherInfo.value,
                status = weatherLoadingStatus.value,
            )
        }

        Text(
            text = "Рекомендации",
            color = colorResource(id = R.color.text),
            fontSize = 31.sp,
            fontWeight = FontWeight(700),
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp),
        )

        when (recomendations.value.size) {
            0 -> {
                Text(
                    text = "Мест рядом нет",
                    color = colorResource(id = R.color.text),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            } else -> {
                LazyColumn {
                    items(recomendations.value) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 10.dp),
                        ) {
                            RecomendationView(recomendation = item, stackNavigator)
                        }
                    }
                }
            }
        }
    }
}