package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.domain.authorization.AllowUser
import com.example.prodfinal.domain.authorization.GetRandomUser
import com.example.prodfinal.domain.authorization.UserInfo
import com.example.prodfinal.domain.model.UserModel
import com.example.prodfinal.presentation.style.getTextFieldColors

@Composable
fun AuthorisationScreen(context: Context, navController: NavController) {
    // Данные с экрана "Создать аккаунт"
    val randomUser = remember {
        mutableStateOf(
            UserModel(
                mutableStateOf(""),
                mutableStateOf(""),
                mutableStateOf(""),
                mutableStateOf(""),
                mutableStateOf(""),
                mutableStateOf(""),
            )
        )
    }

    // Данные с экрана "Войти"
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    // Режим экрана(LOGIN или SIGNIN)
    val mode = remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        mode.value = "" + navController
            .currentBackStackEntry
            ?.arguments
            ?.getString("mode")
        GetRandomUser().getRandomUser(
            context
        ) { response ->
            randomUser.value = response
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(10.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_left_icon),
            contentDescription = "Назад",
            modifier = Modifier
                .clickable (
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    navController.popBackStack()
                }
        )

        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.background))
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
                text = when (mode.value) {
                    "LOGIN" -> "Войти"
                    "SIGNIN" -> "Создать аккаунт"
                    else -> ""
                },
                color = colorResource(id = R.color.text),
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
            )

            if (mode.value == "LOGIN") {
                LogIn(username, password)
            } else if (mode.value == "SIGNIN") {
                SignIn(randomUser)
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (mode.value == "LOGIN") {
                        AllowUser().checkAccess(
                            context,
                            username.value,
                            password.value
                        ) {
                            isRegistered.value = it
                        }
                    } else if (mode.value == "SIGNIN") {
                        UserInfo().saveUser(context, randomUser.value)
                    }
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
                    modifier = Modifier.padding(5.dp),
                    text = "Далее",
                    color = colorResource(id = R.color.background),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

// Блок инпутов в режиме входа
@Composable
fun LogIn(username: MutableState<String>, password: MutableState<String>) {
    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = username.value,
            onValueChange = {
                username.value = it
            },
            label = { Text("Имя") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }
    Column (
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = {
                password.value = it
            },
            label = { Text("Пароль") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }
}

// Блок инпутов в режиме регистрации
@Composable
fun SignIn(randomUser: MutableState<UserModel>) {
    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = randomUser.value.name.value,
            onValueChange = {
                randomUser.value.name.value = it
            },
            label = { Text("Имя") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }

    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = randomUser.value.mail.value,
            onValueChange = {
                randomUser.value.mail.value = it
            },
            label = { Text("Почта") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }

    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = randomUser.value.birthday.value,
            onValueChange = {
                randomUser.value.birthday.value = it
            },
            label = { Text("День рождения") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }

    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = randomUser.value.address.value,
            onValueChange = {
                randomUser.value.address.value = it
            },
            label = { Text("Адрес") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }

    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = randomUser.value.phone_number.value,
            onValueChange = {
                randomUser.value.phone_number.value = it
            },
            label = { Text("Телефон") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }

    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = randomUser.value.password.value,
            onValueChange = {
                randomUser.value.password.value = it
            },
            label = { Text("Пароль") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }
}