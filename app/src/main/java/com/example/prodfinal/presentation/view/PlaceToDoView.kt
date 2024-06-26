package com.example.prodfinal.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.ToDoItemModel
import com.example.prodfinal.presentation.style.getBlackButtonColors

// Блок задачи с локацией для экрана "Мой досуг"

@Composable
fun PlaceToDoView(
    navController: NavController,
    item: ToDoItemModel,
    deleteItem: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (item.isImportant) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.important_icon),
                        contentDescription = "Локация"
                    )
                    // Локация
                    Text(
                        text = "Важно",
                        color = colorResource(id = R.color.error),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Left,
                        fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                    )
                }
            }

            Text(
                text = item.name,
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                color = colorResource(id = R.color.text),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = "Локация"
                )

                Text(
                    text = item.placeName,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            }

            Text(
                text = item.date,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )

            Button(
                onClick = {
                    navController.navigate("recommendation_screen/${item.placeId}")
                },
                colors = getBlackButtonColors(),
            ) {
                Text(
                    text = "К локации",
                    color = colorResource(id = R.color.background),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.delete_icon),
            contentDescription = "Удалить",
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { deleteItem() }
        )
    }
}