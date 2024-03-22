package com.example.prodfinal.presentation.screen

import android.content.Context
import android.net.ConnectivityManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.location.Location
import com.example.prodfinal.data.repository.RecommentadionRepositoryImpl
import com.example.prodfinal.data.repository.WeatherRepositoryImpl
import com.example.prodfinal.domain.model.RecomendationModel
import com.example.prodfinal.domain.model.WeatherModel
import com.example.prodfinal.domain.state.LoadingState
import com.example.prodfinal.presentation.view.RecomendationSkeletonView
import com.example.prodfinal.presentation.view.RecomendationView
import com.example.prodfinal.presentation.view.WeatherView

// Были ли загруженны данные
private var isDataLoaded = mutableStateOf(false)

// Статус виджета погоды(загружается, нет доступа, загружен)
private val loadingStatus = mutableStateOf(LoadingState.LOADING)

// Погода
private val weatherInfo =
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
private var recomendations = mutableStateOf(mutableListOf<RecomendationModel>())

@Composable
fun MainScreen(context: Context, stackNavigator: NavController) {
    isDataLoaded.value = true
    LaunchedEffect(!isDataLoaded.value) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkInfo = connectivityManager.getNetworkInfo(activeNetwork)
        val isConnected = networkInfo != null && networkInfo.isConnected
        if (isConnected) {
            // Получаем данные о геолокации
            Location(context).getLocation { locationResponse ->
                if (!locationResponse.isEnabled) { // Если не удалось получить геолокацию
                    loadingStatus.value = LoadingState.ERROR
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
                    RecommentadionRepositoryImpl().getRecommendations(
                        context,
                        locationResponse.latitude,
                        locationResponse.longtitude
                    ) { recommendationsResponse ->
                        recomendations.value = recommendationsResponse
                        loadingStatus.value = LoadingState.READY // Меняем статус виджета на "загружен"
                    }
                }
            }
        } else {
            loadingStatus.value = LoadingState.ERROR
        }
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
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )

            if (loadingStatus.value == LoadingState.LOADING) {
                RecomendationSkeletonView()
                RecomendationSkeletonView()
                RecomendationSkeletonView()
            } else if (
                recomendations.value.isEmpty() ||
                loadingStatus.value == LoadingState.ERROR
            ) {
                Text(
                    text = "Мест рядом нет",
                    color = colorResource(id = R.color.text),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
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