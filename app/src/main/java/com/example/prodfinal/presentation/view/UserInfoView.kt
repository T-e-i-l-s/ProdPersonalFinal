package com.example.prodfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.UserModel

// Блок задачи с описанием для экрана "Мой досуг"
@Composable
fun UserInfoView (
    item: UserModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
    ) {
        Text(
            text = "Информация",
            fontSize = 19.sp,
            color = colorResource(id = R.color.text),
            fontWeight = FontWeight(700),
            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
        )
        Row {
            Text(
                text = "Почта: ",
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
            Text(
                text = item.mail,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                modifier = Modifier.weight(1f),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        }

        Row {
            Text(
                text = "Телефон: ",
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
            Text(
                text = item.phone_number,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                modifier = Modifier.weight(1f),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        }

        Row {
            Text(
                text = "Адрес: ",
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
            Text(
                text = item.address,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                modifier = Modifier.weight(1f),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        }

        Row {
            Text(
                text = "День рождения: ",
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
            Text(
                text = item.birthday,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                modifier = Modifier.weight(1f),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        }
    }
}