package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.example.prodfinal.data.source.CurrentUserSource
import com.example.prodfinal.domain.model.UserModel
import com.example.prodfinal.domain.state.AuthState
import com.example.prodfinal.presentation.style.getWhiteButtonColors
import com.example.prodfinal.presentation.view.ProfileView
import com.example.prodfinal.presentation.view.UserInfoView

private val isRegistered = mutableStateOf(false) // Авторизован ли пользователь

@Composable
fun UserInfoScreen(context: Context, stackNavigator: NavController) {
    LaunchedEffect(true) {
        isRegistered.value = CurrentUserSource().isRegistered(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isRegistered.value) {
            Registered(context)
        } else {
            NotRegistered(stackNavigator)
        }
    }
}

// Экран с информацией о юзере
@Composable
fun Registered(context: Context) {
    val userInfo = remember {
        mutableStateOf(
            UserModel(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
            )
        )
    }

    LaunchedEffect(true) {
        userInfo.value = CurrentUserSource().getCurrentUser(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
    ) {
        Row {
            Text(
                text = "Профиль",
                color = colorResource(id = R.color.text),
                fontSize = 22.sp,
                fontWeight = FontWeight(700),
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )

            Image(
                painter = painterResource(id = R.drawable.logout_icon),
                contentDescription = "Выйти из аккаунта",
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    CurrentUserSource().logOut(context)
                    isRegistered.value = false
                }
            )
        }

        Box(modifier = Modifier.padding(top = 10.dp)) {
            ProfileView(userInfo.value)
        }

        Box(modifier = Modifier.padding(top = 10.dp)) {
            UserInfoView(userInfo.value)
        }
    }
}

// Экран с предложением авторизоваться
@Composable
fun NotRegistered(stackNavigator: NavController) {
    Text(
        text = "Добро пожаловать в LifestyleHUB!",
        color = colorResource(id = R.color.text),
        fontSize = 19.sp,
        fontWeight = FontWeight(700),
        modifier = Modifier.padding(5.dp),
        fontFamily = FontFamily(Font(R.font.wix_madefor_display))
    )

    Button(
        modifier = Modifier.padding(top = 10.dp),
        onClick = {
            stackNavigator.navigate("authorization_screen/${AuthState.SIGNIN}")
        },
        colors = getWhiteButtonColors(),
    ) {
        Text(
            text = "Создать аккаунт",
            color = colorResource(id = R.color.text),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
        )
    }

    Button(
        modifier = Modifier.padding(top = 10.dp),
        onClick = {
            stackNavigator.navigate("authorization_screen/${AuthState.LOGIN}")
        },
        colors = getWhiteButtonColors(),
    ) {
        Text(
            text = "Войти",
            color = colorResource(id = R.color.text),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
        )
    }
}