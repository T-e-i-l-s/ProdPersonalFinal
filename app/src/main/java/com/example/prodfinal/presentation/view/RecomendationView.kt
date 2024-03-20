package com.example.prodfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.RecomendationModel

// Блок рекомендации для главного экрана
@Composable
fun RecomendationView(
    recomendation: RecomendationModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                navController.navigate(
                    "recomendation_screen/${recomendation.id}"
                )
            }
            .clipToBounds(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Иконка локации
        AsyncImage(
            model = recomendation.image,
            contentDescription = recomendation.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 5.dp, 0.dp),
                    text = recomendation.title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight(700),
                    color = colorResource(id = R.color.text),
                    maxLines = 1
                )
                if (recomendation.category.size > 0) {
                    Text(
                        modifier = Modifier
                            .background(
                                Color.LightGray, shape = RoundedCornerShape(5.dp)
                            )
                            .padding(vertical = 2.dp, horizontal = 5.dp),
                        text = recomendation.category[0],
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.text),
                        maxLines = 1
                    )
                }
            }
            Text(
                text = recomendation.address,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text)
            )
        }
    }
}