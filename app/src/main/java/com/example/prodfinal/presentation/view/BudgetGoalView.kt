package com.example.prodfinal.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodfinal.R
import com.example.prodfinal.domain.model.BudgetGoalModel

// Вью цели для экрана "Бюджет"

@Composable
fun BudgetGoalView(
    item: BudgetGoalModel,
    delete: () -> Unit,
    addToReceived: (value: String) -> Unit,
    focusRequester: FocusRequester
) {
    val goalValue = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
    ) {
        Text(
            text = item.name,
            fontSize = 19.sp,
            fontWeight = FontWeight(700),
            color = colorResource(id = R.color.text),
            fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
            modifier = Modifier.padding(bottom = 3.dp)
        )

        LinearProgressIndicator(
            progress = item.received.toFloat() / item.goal.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(
                    color = colorResource(id = R.color.background),
                    shape = RoundedCornerShape(16.dp)
                ),
            color = colorResource(id = R.color.progress_bar),
            trackColor = colorResource(id = R.color.light_main),
            strokeCap = StrokeCap.Round
        )

        Row(
            modifier = Modifier
                .padding(top = 3.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = item.received.toString(),
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Left
            )

            Text(
                text = item.goal.toString(),
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Right
            )
        }

        Row(
            modifier = Modifier
                .padding(0.dp, 3.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (item.goal != item.received) {
                BasicTextField(
                    value = goalValue.value,
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.light_main),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .weight(1f)
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(fontSize = 16.sp),
                    onValueChange = { goalValue.value = it },
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.padding(5.dp)) {
                            innerTextField()
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Text(
                    text = "руб",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .padding(3.dp, 0.dp, 10.dp, 0.dp)
                )

                Text(
                    text = "Добавить",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.text),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                        .clickable {
                            addToReceived(goalValue.value)
                            goalValue.value = ""
                        },
                    color = colorResource(id = R.color.background),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            } else {
                Text(
                    text = "Цель достигнута",
                    fontSize = 19.sp,
                    color = colorResource(id = R.color.text),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display)),
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Box(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
                Text(
                    text = "Удалить",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.error),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                        .clickable { delete() },
                    color = colorResource(id = R.color.background),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            }
        }
    }
}