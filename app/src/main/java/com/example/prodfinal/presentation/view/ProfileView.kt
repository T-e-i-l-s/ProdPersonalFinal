package com.example.prodfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.UserModel

@Composable
fun ProfileView (
    item: UserModel
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.photo,
            contentDescription = "Фото профиля",
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .clip(CircleShape)
                .border(
                    2.dp,
                    colorResource(id = R.color.text),
                    CircleShape
                ),
            contentScale = ContentScale.FillWidth,
        )

        Text(
            text = item.username,
            color = colorResource(id = R.color.text),
            fontSize = 22.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
            modifier = Modifier
                .weight(1f)
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}
