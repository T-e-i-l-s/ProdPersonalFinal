package com.example.prodfinal.presentation.screen

import android.content.Context
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.prodfinal.R
import com.example.prodfinal.data.location.Location
import com.example.prodfinal.data.repository.RecomentadionRepositoryImpl
import com.example.prodfinal.data.repository.WeatherRepositoryImpl
import com.example.prodfinal.domain.authorization.UserInfo
import com.example.prodfinal.domain.model.RecomendationModel
import com.example.prodfinal.domain.model.WeatherModel
import com.example.prodfinal.presentation.view.RecomendationView
import com.example.prodfinal.presentation.view.WeatherView

// Начата ли загрузка данных
var isWeatherLoading = false

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

var recomendations = mutableStateOf(mutableListOf<RecomendationModel>())

@Composable
fun MainScreen(context: Context) {
    if (!isWeatherLoading) { // Если данные не загружены
        isWeatherLoading = true
        // Получаем данные о геолокации
        Location(context).getLocation { locationResponse ->
            if (!locationResponse.isEnabled) { // Если не удалось получить геолокацию
                weatherLoadingStatus.value = "ERROR"
            } else { // Удалось получить геолокацию
                // Получаем погоду на данных координатах
                WeatherRepositoryImpl().getWeather(
                    context,
                    55.78, 49.12
//                    locationResponse.latitude,
//                    locationResponse.longtitude
                ) { weatherResponse ->
                    weatherInfo.value = weatherResponse // Сохраняем данные о погоде
                    weatherLoadingStatus.value = "READY" // Меняем статус виджета на "загружен"
                }

                // Получаем рекомендации на данных координатах
                RecomentadionRepositoryImpl().getRecomendations(
                    context,
                    55.78, 49.12
//                    locationResponse.latitude,
//                    locationResponse.longtitude
                ) { recomendationsResponse ->
                    recomendations.value = recomendationsResponse
                }
            }
        }
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
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            WeatherView(
                weatherItem = weatherInfo.value,
                status = weatherLoadingStatus.value,
            )
        }

        LazyColumn {
            items(recomendations.value) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                ) {
                    RecomendationView(recomendation = item)
                }
            }
        }
    }
}