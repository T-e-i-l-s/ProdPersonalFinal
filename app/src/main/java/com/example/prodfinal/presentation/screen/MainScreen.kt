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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.example.prodfinal.presentation.view.RecomendationSkeletonView
import com.example.prodfinal.presentation.view.RecomendationView
import com.example.prodfinal.presentation.view.WeatherView

// Были ли загруженны данные
var isDataLoaded = false

// Статус виджета погоды(загружается, нет доступа, загружен)
val loadingStatus = mutableStateOf("LOADING")

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
                loadingStatus.value = "ERROR"
            } else { // Удалось получить геолокацию
                // Получаем погоду на данных координатах
                WeatherRepositoryImpl().getWeather(
                    context,
                    locationResponse.latitude,
                    locationResponse.longtitude
                ) { weatherResponse ->
                    weatherInfo.value = weatherResponse // Сохраняем данные о погоде
                }

                // Получаем рекомендации на данных координатах
                RecomentadionRepositoryImpl().getRecomendations(
                    context,
                    locationResponse.latitude,
                    locationResponse.longtitude
                ) { recomendationsResponse ->
                    recomendations.value = recomendationsResponse
                    loadingStatus.value = "READY" // Меняем статус виджета на "загружен"
                }
            }
        }
        isDataLoaded = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.weather),
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            WeatherView(
                weatherItem = weatherInfo.value,
                status = loadingStatus.value,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    colorResource(id = R.color.background),
                    RoundedCornerShape(
                        topEnd = 24.dp,
                        topStart = 24.dp
                    )
                )
                .padding(10.dp, 0.dp, 10.dp, 0.dp),
        ) {

            Text(
                text = "Рекомендации",
                color = colorResource(id = R.color.text),
                fontSize = 25.sp,
                fontWeight = FontWeight(600),
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp),
            )

            if (loadingStatus.value == "LOADING") {
                RecomendationSkeletonView()
                RecomendationSkeletonView()
                RecomendationSkeletonView()
            } else if (
                recomendations.value.isEmpty() ||
                loadingStatus.value == "ERROR"
            ) {
                Text(
                    text = "Мест рядом нет",
                    color = colorResource(id = R.color.text),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(recomendations.value) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                                .shadow(
                                    elevation = 5.dp,
                                    shape = RoundedCornerShape(16.dp),
                                    spotColor = colorResource(id = R.color.shadow)
                                ),
                        ) {
                            RecomendationView(recomendation = item, stackNavigator)
                        }
                    }
                }
            }

        }
    }
}