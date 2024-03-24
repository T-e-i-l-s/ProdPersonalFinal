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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.source.BudgetSource
import com.example.prodfinal.domain.model.BudgetGoalModel
import com.example.prodfinal.navigation.stackCurrentRoute
import com.example.prodfinal.presentation.style.getTextFieldColors
import java.math.BigDecimal

@Composable
fun CreateGoalScreen(context: Context, navController: NavController) {
    // Название цели
    val name = remember {
        mutableStateOf("")
    }
    // Цель(сумма)
    val goal = remember {
        mutableStateOf("")
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
                    if (stackCurrentRoute.value == "create_goal_screen") {
                        navController.popBackStack()
                    }
                }
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = name.value,
                onValueChange = {
                    name.value = it
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
                value = goal.value,
                onValueChange = {
                    goal.value = it
                },
                label = {
                    Text(
                        text = "Сумма",
                        fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                    )
                },
                colors = getTextFieldColors(),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }


        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    BudgetSource().createGoal(
                        context,
                        BudgetGoalModel(
                            name.value,
                            goal.value.toBigDecimalOrNull() ?: BigDecimal(0),
                            BigDecimal(0)
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
                        .padding(5.dp),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            }
        }
    }
}