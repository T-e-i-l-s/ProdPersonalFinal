package com.example.prodfinal.presentation.screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.local.AddToDo
import com.example.prodfinal.domain.model.ToDoItemModel
import org.json.JSONObject

val toDoName = mutableStateOf("")
val toDoDescription = mutableStateOf("")
val toDoDate = mutableStateOf("")

@Composable
fun CreateToDoScreen(context: Context, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_left_icon),
            contentDescription = "Назад",
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = toDoName.value,
                onValueChange = {
                    toDoName.value = it
                },
                label = { Text("Название") }
            )
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = toDoDescription.value,
                onValueChange = {
                    toDoDescription.value = it
                },
                label = { Text("Описание") }
            )
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = toDoDate.value,
                onValueChange = {
                    toDoDate.value = it
                },
                label = { Text("Дата") }
            )
        }

        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    AddToDo().addToDo(
                        context, ToDoItemModel(
                            "",
                            toDoName.value,
                            toDoDescription.value,
                            toDoDate.value,
                            ""
                        )
                    )
                    navController.navigate("main_component")
                },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                ),
            ) {
                Text(
                    text = "Добавить",
                    color = colorResource(id = R.color.background),
                    fontWeight = FontWeight(700),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
    }
}