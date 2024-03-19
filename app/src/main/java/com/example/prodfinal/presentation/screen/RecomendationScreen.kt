package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prodfinal.R
import com.example.prodfinal.data.repository.RecomendationInfoRepositoryImpl
import com.example.prodfinal.domain.model.FullRecomendationModel

@Composable
fun RecomendationScreen(context: Context, navController: NavController) {
    val fsqId = remember {
        mutableStateOf("")
    }

    val placeInfo = remember {
        mutableStateOf(
            FullRecomendationModel(
                "",
                "",
                ArrayList(),
                "",
                ArrayList(),
                ""
            )
        )
    }

    LaunchedEffect(true) {
        fsqId.value = "" + navController.currentBackStackEntry?.arguments?.getString("fsq_id")
        RecomendationInfoRepositoryImpl().getRecomendations(
            context,
            "" + fsqId.value
        ) { response ->
            placeInfo.value = response
        }
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
                .clickable {
                    navController.popBackStack()
                }
        )
        AsyncImage(
            model =
            if (placeInfo.value.photos.isEmpty()) {
                ""
            } else {
                placeInfo.value.photos[0]
            },
            contentDescription = placeInfo.value.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(
                    width = 3.dp,
                    color = colorResource(id = R.color.background)
                ),
            contentScale = ContentScale.FillWidth,
        )
        LazyRow {
            items(placeInfo.value.photos) {
                AsyncImage(
                    model = it,
                    contentDescription = "Фото локации",
                    modifier = Modifier
                        .width(156.dp)
                        .height(96.dp)
                        .border(
                            width = 3.dp,
                            color = colorResource(id = R.color.background)
                        ),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
        Text(
            text = placeInfo.value.title,
            fontSize = 25.sp,
            fontWeight = FontWeight(700),
            color = colorResource(id = R.color.text),
            modifier = Modifier
                .padding(
                    10.dp,
                    10.dp,
                    0.dp,
                    0.dp
                )
        )

        LazyRow {
            items(placeInfo.value.category) {
                Box(
                    modifier = Modifier
                        .padding(
                            10.dp,
                            10.dp,
                            0.dp,
                            0.dp
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                Color.LightGray, shape = RoundedCornerShape(5.dp)
                            )
                            .padding(vertical = 2.dp, horizontal = 5.dp),
                        text = it,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.text),
                        maxLines = 1
                    )
                }
            }
        }

        Text(
            text = "Адрес: " + placeInfo.value.address,
            fontSize = 16.sp,
            color = colorResource(id = R.color.text),
            modifier = Modifier
                .padding(
                    10.dp,
                    10.dp,
                    0.dp,
                    0.dp
                )
        )

        if (placeInfo.value.mail != null) {
            Text(
                text = "Эл. почта: " + placeInfo.value.mail,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text),
                modifier = Modifier
                    .padding(
                        10.dp,
                        10.dp,
                        0.dp,
                        0.dp
                    )
            )
        }


        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate(
                        "create_todo_screen/PLACE/${placeInfo.value.id}/${placeInfo.value.title}"
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                    colorResource(id = R.color.text),
                ),
            ) {
                Text(
                    text = "Добавить в мой досуг",
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