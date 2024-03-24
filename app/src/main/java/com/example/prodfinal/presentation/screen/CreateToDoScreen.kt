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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import com.example.prodfinal.navigation.stackCurrentRoute
import com.example.prodfinal.presentation.style.getTextFieldColors

@Composable
fun CreateToDoScreen(context: Context, navController: NavController) {
    // Режим экрана
    val mode = remember {
        mutableStateOf(ToDoState.TEXT)
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
        mode.value = ToDoState.valueOf(
            navController.currentBackStackEntry
                ?.arguments
                ?.getString("mode").toString()
        )
        placeId.value = navController.currentBackStackEntry
            ?.arguments
            ?.getString("place_id").toString()
        placeName.value = navController.currentBackStackEntry
            ?.arguments
            ?.getString("place_name").toString()
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
                    if (stackCurrentRoute.value == "create_todo_screen") {
                        navController.popBackStack()
                    }
                }
        )

        if (mode.value == ToDoState.TEXT) {
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
                    // Получаем ToDoItemModel из полученных данных
                    val result: ToDoItemModel
                    if (mode.value == ToDoState.TEXT) {
                        result = ToDoItemModel(
                            ToDoState.TEXT,
                            toDoName.value,
                            toDoDescription.value,
                            toDoDate.value,
                            "",
                            ""
                        )
                    } else {
                        result = ToDoItemModel(
                            ToDoState.PLACE,
                            toDoName.value,
                            "",
                            toDoDate.value,
                            "" + placeId.value,
                            "" + placeName.value
                        )
                    }

                    // Сохраняем данные
                    ToDoSource().addToDo(
                        context,
                        result
                    )

                    // Сбрасываем все поля
                    toDoName.value = ""
                    toDoDescription.value = ""
                    toDoDate.value = ""

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
                        .padding(5.dp),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
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
            label = {
                Text(
                    text = "Название",
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            },
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
            value = toDoDescription.value,
            onValueChange = {
                toDoDescription.value = it
            },
            label = {
                Text(
                    text = "Описание",
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            },
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
            label = {
                Text(
                    text = "Дата",
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            },
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
        fontFamily = FontFamily(Font(R.font.wix_madefor_display))
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
            label = {
                Text(
                    text = "Название",
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            },
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
            label = {
                Text(
                    text = "Дата",
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            },
        )
    }
}