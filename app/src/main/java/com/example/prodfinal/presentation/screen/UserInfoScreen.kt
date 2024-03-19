package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.domain.authorization.UserInfo
import com.example.prodfinal.domain.model.UserModel

var isRegistered = mutableStateOf(false)
private var userIsLoading = false
private var userInfo = mutableStateOf(
    UserModel(
        mutableStateOf(""),
        mutableStateOf(""),
        mutableStateOf(""),
        mutableStateOf(""),
        mutableStateOf(""),
        mutableStateOf(""),
    )
)

@Composable
fun UserInfoScreen(context: Context, stackNavigator: NavController) {
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
    if (!userIsLoading) {
        userIsLoading = true
        UserInfo().getUser(context) {
            userInfo.value = it
        }
    }
    Text(
        text = userInfo.value.name.value,
        color = colorResource(id = R.color.text),
        fontSize = 19.sp,
        fontWeight = FontWeight(700),
        modifier = Modifier.padding(5.dp)
    )
}

// Экран с предложение авторизоваться
@Composable
fun NotRegistered(stackNavigator: NavController) {
    Text(
        text = "Добро пожаловать в LifestyleHUB!",
        color = colorResource(id = R.color.text),
        fontSize = 19.sp,
        fontWeight = FontWeight(700),
        modifier = Modifier.padding(5.dp)
    )

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Button(
            onClick = {
                stackNavigator.navigate("authorization_screen/SIGNIN")
            },
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.main),
                colorResource(id = R.color.main),
                colorResource(id = R.color.selected),
                colorResource(id = R.color.selected),
            ),
        ) {
            Text(
                text = "Создать аккаунт",
                color = colorResource(id = R.color.text),
                fontSize = 16.sp,
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Button(
            onClick = {
                stackNavigator.navigate("authorization_screen/LOGIN")
            },
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.main),
                colorResource(id = R.color.main),
                colorResource(id = R.color.selected),
                colorResource(id = R.color.selected),
            ),
        ) {
            Text(
                text = "Войти",
                color = colorResource(id = R.color.text),
                fontSize = 16.sp,
            )
        }
    }
}