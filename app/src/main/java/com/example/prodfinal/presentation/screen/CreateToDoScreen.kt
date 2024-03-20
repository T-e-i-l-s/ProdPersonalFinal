package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.local.AddToDo
import com.example.prodfinal.domain.model.ToDoItemModel
import com.example.prodfinal.navigation.currentRoute
import com.example.prodfinal.navigation.selectedItem
import com.example.prodfinal.presentation.style.getTextFieldColors

@Composable
fun CreateToDoScreen(context: Context, navController: NavController) {
    // Режим экрана(TEXT или PLACE)
    val mode = remember {
        mutableStateOf("")
    }
    // Id места(нужно для PlaceToDo)
    val placeId = remember {
        mutableStateOf("")
    }
    // Название места(нужно для PlaceToDo)
    val placeName = remember {
        mutableStateOf("")
    }

    // Название задачи
    val toDoName = remember {
        mutableStateOf("")
    }
    // Описание задачи(для TextToDo)
    val toDoDescription = remember {
        mutableStateOf("")
    }
    // Дедлайн задачи
    val toDoDate = remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        mode.value = "" + navController.currentBackStackEntry?.arguments?.getString("mode")
        placeId.value = "" + navController.currentBackStackEntry?.arguments?.getString("place_id")
        placeName.value =
            "" + navController.currentBackStackEntry?.arguments?.getString("place_name")
    }

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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    navController.popBackStack()
                }
        )

        if (mode.value == "TEXT") {
            TextToDo(toDoName, toDoDescription, toDoDate)
        } else {
            PlaceToDo(toDoName, toDoDate, "" + placeName.value)
        }


        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    val result: ToDoItemModel
                    if (mode.value == "TEXT") {
                        result = ToDoItemModel(
                            "text",
                            toDoName.value,
                            toDoDescription.value,
                            toDoDate.value,
                            "",
                            ""
                        )
                    } else {
                        result = ToDoItemModel(
                            "place",
                            toDoName.value,
                            "",
                            toDoDate.value,
                            "" + placeId.value,
                            "" + placeName.value
                        )
                    }

                    toDoName.value = ""
                    toDoDescription.value = ""
                    toDoDate.value = ""

                    AddToDo().addToDo(
                        context,
                        result
                    )
                    selectedItem.value = 1
                    currentRoute.value = "todo_screen"
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

@Composable
fun TextToDo(
    toDoName: MutableState<String>,
    toDoDescription: MutableState<String>,
    toDoDate: MutableState<String>
) {
    Box(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = toDoName.value,
            onValueChange = {
                toDoName.value = it
            },
            label = { Text("Название") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp)
        )
    }

    Box(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = toDoDescription.value,
            onValueChange = {
                toDoDescription.value = it
            },
            label = { Text("Описание") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }

    Box(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = toDoDate.value,
            onValueChange = {
                toDoDate.value = it
            },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
            label = { Text("Дата") }
        )
    }
}

@Composable
fun PlaceToDo(
    toDoName: MutableState<String>,
    toDoDate: MutableState<String>,
    placeName: String
) {
    Text(
        text = "Место: $placeName",
        modifier = Modifier
            .padding(10.dp, 0.dp, 10.dp, 0.dp),
        fontSize = 19.sp,
        fontWeight = FontWeight(700),
        color = colorResource(id = R.color.text),
    )

    Box(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = toDoName.value,
            onValueChange = {
                toDoName.value = it
            },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
            label = { Text("Название") }
        )
    }

    Box(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = toDoDate.value,
            onValueChange = {
                toDoDate.value = it
            },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
            label = { Text("Дата") }
        )
    }
}