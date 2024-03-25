package com.example.prodfinal.presentation.screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.prodfinal.data.repository.RecommendationsRepositoryImpl
import com.example.prodfinal.data.repository.WeatherRepositoryImpl
import com.example.prodfinal.domain.model.ShortRecommendationModel
import com.example.prodfinal.domain.model.WeatherModel
import com.example.prodfinal.domain.state.LoadingState
import com.example.prodfinal.presentation.view.RecommendationView
import com.example.prodfinal.presentation.view.SceletonView
import com.example.prodfinal.presentation.view.WeatherView


// Загружены ли данные
private var isDataLoaded = false

// Статус загрузки данных(загружается, нет доступа, загружен)
private val loadingStatus = mutableStateOf(LoadingState.LOADING)

// Погода
private val weatherInfo = mutableStateOf(
    WeatherModel("", "", "", "", "", "", "")
)

// Список рекомендаций(места рядом)
private var recomendations = mutableListOf<ShortRecommendationModel>()

// Функция, которая проверяет наличае подключения к интернету
fun checkNetwork(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var isConnected = false
    val network: Network? = cm.activeNetwork
    if (network != null) {
        val capabilities: NetworkCapabilities? = cm.getNetworkCapabilities(network)
        if (capabilities != null) {
            isConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return isConnected
}

@Composable
fun MainScreen(context: Context, stackNavigator: NavController) {
    if (!isDataLoaded) {
        isDataLoaded = true
        // Проверяем подключен ли интернет
        val isConnected = checkNetwork(context)
        if (isConnected) { // Интернет подключен
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
                    RecommendationsRepositoryImpl().getRecommendations(
                        context,
                        locationResponse.latitude,
                        locationResponse.longtitude
                    ) { recommendationsResponse ->
                        recomendations = recommendationsResponse
                        // Меняем статус загрузки на "загружено"
                        loadingStatus.value = LoadingState.READY
                    }
                }
            }
        } else {
            // Нет интернета => выдаем ошибку загрузки
            loadingStatus.value = LoadingState.ERROR
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.weather))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
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
                .padding(horizontal = 10.dp),
        ) {
            Text(
                text = "Рекомендации",
                color = colorResource(id = R.color.text),
                fontSize = 25.sp,
                fontWeight = FontWeight(600),
                modifier = Modifier
                    .padding(vertical = 10.dp),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )

            if (loadingStatus.value == LoadingState.LOADING) {
                for (i in 0..2) {
                    SceletonView(
                        Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(bottom = 10.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            } else if (recomendations.isEmpty() || loadingStatus.value == LoadingState.ERROR) {
                Text(
                    text = "Мест рядом нет",
                    color = colorResource(id = R.color.text),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            } else {
                LazyColumn {
                    items(recomendations) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                                .shadow(
                                    elevation = 5.dp,
                                    shape = RoundedCornerShape(16.dp),
                                    spotColor = colorResource(id = R.color.shadow)
                                ),
                        ) {
                            RecommendationView(recommendation = item, stackNavigator)
                        }
                    }
                }
            }

        }
    }
}