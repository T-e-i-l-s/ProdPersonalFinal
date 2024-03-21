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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.ToDoItemModel

// Блок задачи с описанием для экрана "Мой досуг"
@Composable
fun TextToDoView(
    item: ToDoItemModel,
    deleteItem: () -> Unit
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
            Text(
                text = item.name,
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                color = colorResource(id = R.color.text),
            )
            Text(
                text = item.description,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
            )
            Text(
                text = item.date,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
            )
        }
        Image(
            painter = painterResource(id = R.drawable.delete_icon),
            contentDescription = "Удалить",
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                .clickable (
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    deleteItem()
                }
        )
    }
}