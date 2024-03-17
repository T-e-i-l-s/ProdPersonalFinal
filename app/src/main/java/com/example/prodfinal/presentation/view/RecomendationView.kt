package com.example.prodfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.RecomendationModel

@Composable
fun RecomendationView(recomendation: RecomendationModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Иконка локации
        AsyncImage(
            model = recomendation.image,
            contentDescription = recomendation.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = recomendation.title,
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                color = colorResource(id = R.color.text)
            )
            Text(
                text = recomendation.address,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text)
            )
        }
    }
}