package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import com.example.prodfinal.data.source.ToDoSource
import com.example.prodfinal.domain.model.ToDoItemModel
import com.example.prodfinal.domain.state.ToDoState
import com.example.prodfinal.navigation.stackCurrentRoute
import com.example.prodfinal.presentation.style.getBlackButtonColors
import com.example.prodfinal.presentation.style.getCheckBoxColors
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

    // Важная ли задача
    val isImportant = remember {
        mutableStateOf(false)
    }

    val createToDo = {
        // Получаем ToDoItemModel из полученных данных
        val result: ToDoItemModel
        if (mode.value == ToDoState.TEXT) {
            result = ToDoItemModel(
                ToDoState.TEXT,
                isImportant.value,
                toDoName.value,
                toDoDescription.value,
                toDoDate.value,
                "",
                ""
            )
        } else {
            result = ToDoItemModel(
                ToDoState.PLACE,
                isImportant.value,
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
    }

    LaunchedEffect(true) {
        // Получаем аргументы, переданные при навигации
        mode.value = ToDoState.valueOf(
            navController.currentBackStackEntry?.arguments?.getString("mode").toString()
        )
        placeId.value =
            navController.currentBackStackEntry?.arguments?.getString("place_id").toString()
        placeName.value =
            navController.currentBackStackEntry?.arguments?.getString("place_name").toString()
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)
        ) {
            Checkbox(
                checked = isImportant.value,
                onCheckedChange = { isImportant.value = it },
                colors = getCheckBoxColors()
            )

            Text(
                text = "Отметить как важное",
                color = colorResource(id = R.color.text),
                fontSize = 16.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Left,
                fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    isImportant.value = !isImportant.value
                }
            )
        }

        Button(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            onClick = { createToDo() },
            colors = getBlackButtonColors(),
        ) {
            Text(
                text = "Добавить",
                color = colorResource(id = R.color.background),
                fontWeight = FontWeight(700),
                fontSize = 16.sp,
                modifier = Modifier.padding(5.dp),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        }
    }
}

@Composable
fun TextToDo(
    toDoName: MutableState<String>,
    toDoDescription: MutableState<String>,
    toDoDate: MutableState<String>
) {
    TextField(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        value = toDoName.value,
        onValueChange = { toDoName.value = it },
        label = {
            Text(
                text = "Название",
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )

    TextField(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth(),
        value = toDoDescription.value,
        onValueChange = { toDoDescription.value = it },
        label = {
            Text(
                text = "Описание",
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )

    TextField(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth(),
        value = toDoDate.value,
        onValueChange = { toDoDate.value = it },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = "Выполнить до",
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        },
    )
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
            .padding(horizontal = 10.dp),
        fontSize = 19.sp,
        fontWeight = FontWeight(700),
        color = colorResource(id = R.color.text),
        fontFamily = FontFamily(Font(R.font.wix_madefor_display))
    )

    TextField(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth(),
        value = toDoName.value,
        onValueChange = { toDoName.value = it },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = "Название",
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        },
    )

    TextField(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth(),
        value = toDoDate.value,
        onValueChange = { toDoDate.value = it },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = "Выполнить до",
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )
        },
    )
}