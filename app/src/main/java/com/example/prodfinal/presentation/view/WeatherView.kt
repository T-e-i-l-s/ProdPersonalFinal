package com.example.prodfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.WeatherModel

// Блок погоды для главного экрана
@Composable
fun WeatherView(weatherItem: WeatherModel, status: String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (status == "READY") { // Если данные получены

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Иконка погоды
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${weatherItem.icon}@2x.png",
                    contentDescription = weatherItem.weather,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            10.dp,
                            0.dp,
                            0.dp,
                            0.dp
                        )

                ) {
                    // Локация
                    Text(
                        text = weatherItem.city,
                        color = colorResource(id = R.color.text),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left
                    )

                    // Температура
                    Text(
                        text = weatherItem.currentTemprature + "°",
                        color = colorResource(id = R.color.text),
                        fontSize = 25.sp,
                        fontWeight = FontWeight(700)
                    )
                    // Мин и макс температура
                    Text(
                        text = "От ${weatherItem.minTemprature}° " +
                                "до ${weatherItem.maxTemprature}°",
                        color = colorResource(id = R.color.text),
                        fontSize = 16.sp
                    )
                    // Ощущается как
                    Text(
                        text = "Ощущается как ${weatherItem.feelsLike}°",
                        color = colorResource(id = R.color.text),
                        fontSize = 16.sp
                    )
                    // Название погоды(ясно, облачно и т.д.)
                    Text(
                        text = weatherItem.weather,
                        color = colorResource(id = R.color.text),
                        fontSize = 16.sp
                    )

                }

            }

        } else if (status == "LOADING") { // Если данные загружаются

            CircularProgressIndicator(
                modifier = Modifier.padding(10.dp),
                color = colorResource(id = R.color.text),
                trackColor = colorResource(id = R.color.selected),
            )

        } else { // Если произошла ошибка

            Text(
                text = "Невозможно получить данные",
                color = colorResource(id = R.color.text),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

        }

    }

}
