package com.example.prodfinal.presentation.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodfinal.R
import com.example.prodfinal.data.repository.ValuteRepositoryImpl
import com.example.prodfinal.domain.model.ValuteExchangeModel
import com.example.prodfinal.domain.state.ValuteRate
import java.math.BigDecimal

// Были ли загружены данные
private val isDataLoaded = mutableStateOf(false)

@Composable
fun ValuteView(context: Context) {
    val valuteData = remember {
        mutableStateOf(
            ValuteExchangeModel(
                BigDecimal(0),
                BigDecimal(0),
                ValuteRate.FALL,
                BigDecimal(0),
                BigDecimal(0),
                ValuteRate.FALL,
            )
        )
    }

    LaunchedEffect(!isDataLoaded.value) {
        ValuteRepositoryImpl().getValuteExchange(context) { response ->
            isDataLoaded.value = true
            valuteData.value = response
        }
    }

    Box(
        modifier = Modifier
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
    ) {
        if (isDataLoaded.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.main),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = when (valuteData.value.usdRate) {
                            ValuteRate.FALL -> painterResource(id = R.drawable.down_arrow_icon)
                            ValuteRate.RISE -> painterResource(id = R.drawable.up_arrow_icon)
                        },
                        contentDescription = "Направление курса"
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "USD",
                            color = colorResource(id = R.color.text),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                        )

                        Text(
                            text = valuteData.value.currentUsd.toString() + "₽",
                            color = colorResource(id = R.color.text),
                            fontSize = 25.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                        )
                    }
                }

                Row(
                    Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "EUR",
                            color = colorResource(id = R.color.text),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                        )

                        Text(
                            text = valuteData.value.currentEur.toString() + "₽",
                            color = colorResource(id = R.color.text),
                            fontSize = 25.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                        )
                    }

                    Image(
                        painter = when (valuteData.value.eurRate) {
                            ValuteRate.FALL -> painterResource(id = R.drawable.down_arrow_icon)
                            ValuteRate.RISE -> painterResource(id = R.drawable.up_arrow_icon)
                        },
                        contentDescription = "Направление курса"
                    )
                }
            }
        } else {
            SceletonView(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
    }
}