package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prodfinal.R
import com.example.prodfinal.data.repository.RecommendationInfoRepositoryImpl
import com.example.prodfinal.data.source.RecomendationSource
import com.example.prodfinal.domain.model.FullRecommendationModel
import com.example.prodfinal.domain.state.LoadingState
import com.example.prodfinal.navigation.stackCurrentRoute
import com.example.prodfinal.presentation.style.getBlackButtonColors
import com.example.prodfinal.presentation.view.ImageSkeletonView
import com.example.prodfinal.presentation.view.NoPhotoView

@Composable
fun RecomendationScreen(context: Context, navController: NavController) {
    // Статус загрузки данных
    val loadingStatus = remember {
        mutableStateOf(LoadingState.LOADING)
    }
    // fsq_id выбранной рекомендации
    val fsqId = remember {
        mutableStateOf("")
    }
    // Информация о рекомендации
    val placeInfo = remember {
        mutableStateOf(
            FullRecommendationModel(
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
        // Получаем fsq_id
        fsqId.value = navController.currentBackStackEntry
            ?.arguments
            ?.getString("fsq_id").toString()

        // Делаем запрос на данные о рекомендации
        RecommendationInfoRepositoryImpl().getRecommendationInfo(
            context,
            "" + fsqId.value
        ) { response ->
            // Выводим полученные данные
            placeInfo.value = response
            RecomendationSource().saveRecomendationData(context, response)
            loadingStatus.value = LoadingState.READY
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    if (stackCurrentRoute.value == "recommendation_screen") {
                        navController.popBackStack()
                    }
                }
        )

        // Основное изображение
        if (loadingStatus.value == LoadingState.LOADING) {
            ImageSkeletonView(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(
                        width = 3.dp,
                        color = colorResource(id = R.color.background)
                    )
            )
        } else if (placeInfo.value.photos.size > 0) {
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
        } else {
            NoPhotoView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(
                        width = 3.dp,
                        color = colorResource(id = R.color.background)
                    ),
            )
        }

        // Все фотографии
        if (loadingStatus.value == LoadingState.LOADING) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (i in 0..2) {
                    ImageSkeletonView(
                        Modifier
                            .width(156.dp)
                            .height(96.dp)
                            .border(
                                width = 3.dp,
                                color = colorResource(id = R.color.background)
                            )
                    )
                }
            }
        } else if (placeInfo.value.photos.size > 1) {
            LazyRow {
                items(placeInfo.value.photos.subList(1, placeInfo.value.photos.size)) {
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
        }

        // Инфа о локации
        if (loadingStatus.value == LoadingState.READY) {
            Text(
                text = placeInfo.value.title,
                fontSize = 25.sp,
                fontWeight = FontWeight(700),
                color = colorResource(id = R.color.text),
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )

            LazyRow (
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(placeInfo.value.category) {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                            .padding(vertical = 2.dp, horizontal = 5.dp),
                        text = it,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.text),
                        maxLines = 1,
                        fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                    )
                }
            }

            if (placeInfo.value.address.isNotEmpty()) {
                Text(
                    text = "Адрес: " + placeInfo.value.address,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text),
                    modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            }

            if (placeInfo.value.mail != null) {
                Text(
                    text = "Эл. почта: " + placeInfo.value.mail,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text),
                    modifier = Modifier
                        .padding(10.dp, 10.dp, 10.dp, 0.dp),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            }

            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate(
                        "create_todo_screen/PLACE/${placeInfo.value.id}/${placeInfo.value.title}"
                    )
                },
                colors = getBlackButtonColors(),
            ) {
                Text(
                    text = "Добавить в \"Мой досуг\"",
                    color = colorResource(id = R.color.background),
                    fontWeight = FontWeight(700),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(5.dp),
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            }
        }
    }
}