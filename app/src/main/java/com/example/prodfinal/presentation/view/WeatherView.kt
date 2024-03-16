package com.example.prodfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.WeatherItem

@Composable
fun WeatherView (weatherItem: WeatherItem) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
    ) {
        Box {
            Text(
                text = weatherItem.city,
                color = colorResource(id = R.color.text),
                fontSize = 16.sp,
                fontWeight = FontWeight(700)
            )
        }
        Row (
            modifier = Modifier
                .padding(
                    0.dp,
                    10.dp,
                    0.dp,
                    0.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box (
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .background(Color.Gray)
            ) {
            }
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        10.dp,
                        0.dp,
                        0.dp,
                        0.dp
                    )
            ) {
                Text(
                    text = weatherItem.currentTemprature,
                    color = colorResource(id = R.color.text),
                    fontSize = 25.sp,
                    fontWeight = FontWeight(700)
                )
                Text(
                    text = "От ${weatherItem.minTemprature} " +
                            "до ${weatherItem.maxTemprature}",
                    color = colorResource(id = R.color.text),
                    fontSize = 16.sp
                )
                Text(
                    text = "Ощущается как ${weatherItem.feelsLike} ",
                    color = colorResource(id = R.color.text),
                    fontSize = 16.sp
                )
                Text(
                    text = weatherItem.weather,
                    color = colorResource(id = R.color.text),
                    fontSize = 16.sp
                )
            }
        }
    }
}
