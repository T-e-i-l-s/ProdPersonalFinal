package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.source.ToDoSource
import com.example.prodfinal.domain.model.ToDoItemModel
import com.example.prodfinal.domain.state.ToDoState
import com.example.prodfinal.presentation.view.PlaceToDoView
import com.example.prodfinal.presentation.view.TextToDoView

private var todoList = mutableStateOf(listOf<ToDoItemModel>())

@Composable
fun ToDoScreen(context: Context, navController: NavController) {
    LaunchedEffect(true) {
        todoList.value = ToDoSource().getToDo(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
        ) {
            Text(
                text = "Мой досуг",
                color = colorResource(id = R.color.text),
                fontSize = 22.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = "Добавить",
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        navController.navigate("create_todo_screen/TEXT/_/_")
                    }
            )
        }

        LazyColumn {
            itemsIndexed(todoList.value) { index, item ->
                Box(modifier = Modifier.padding(bottom = 10.dp)) {
                    if (item.mode == ToDoState.TEXT) {
                        TextToDoView(item) {
                            todoList.value = ToDoSource().deleteToDo(context, index)
                        }
                    } else {
                        PlaceToDoView(navController, item) {
                            todoList.value = ToDoSource().deleteToDo(context, index)
                        }
                    }
                }
            }
        }
    }
}
