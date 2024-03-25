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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.source.BudgetSource
import com.example.prodfinal.domain.model.BudgetGoalModel
import com.example.prodfinal.presentation.view.BudgetGoalView
import com.example.prodfinal.presentation.view.ValuteView

private val goals = mutableStateOf(listOf<BudgetGoalModel>())

@Composable
fun BudgetScreen(context: Context, navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(true) {
        goals.value = BudgetSource().getGoals(context)
    }

    Column(
        Modifier
            .background(colorResource(id = R.color.background))
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Бюджет",
                color = colorResource(id = R.color.text),
                fontSize = 22.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
            )

            Image(
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = "Добавить",
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        navController.navigate("create_goal_screen")
                    }
            )
        }

        ValuteView(context = context)

        LazyColumn {
            itemsIndexed(goals.value) { index, item ->
                Box(modifier = Modifier.padding(bottom = 10.dp)) {
                    BudgetGoalView(
                        item,
                        {
                            goals.value = BudgetSource().deleteGoal(context, index)
                        },
                        { value ->
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            goals.value = BudgetSource().addToReceived(context, index, value)
                        },
                        focusRequester
                    )
                }
            }
        }
    }
}