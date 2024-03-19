package com.example.prodfinal.presentation.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.local.DeleteToDo
import com.example.prodfinal.data.local.GetToDoList
import com.example.prodfinal.domain.model.ToDoItemModel
import com.example.prodfinal.presentation.view.PlaceToDoView
import com.example.prodfinal.presentation.view.TextToDoView

@Composable
fun ToDoScreen(context: Context, navController: NavController) {
    val todoList = remember {
        mutableStateOf(arrayListOf<ToDoItemModel>())
    }

    LaunchedEffect(true) {
        todoList.value = GetToDoList().getToDo(context)
        Log.e("TODO_LIST", todoList.value.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = "Добавить",
                modifier = Modifier
                    .clickable {
                        navController.navigate("create_todo_screen/TEXT/_/_")
                    }
            )
        }

        LazyColumn {
            itemsIndexed(todoList.value) { index, item ->
                Box(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)) {
                    if (item.mode == "text") {
                        TextToDoView(item) {
                            todoList.value = DeleteToDo().deleteToDo(context, index)
                        }
                    } else {
                        PlaceToDoView(navController, item) {
                            todoList.value = DeleteToDo().deleteToDo(context, index)
                        }
                    }
                }
            }
        }
    }
}
