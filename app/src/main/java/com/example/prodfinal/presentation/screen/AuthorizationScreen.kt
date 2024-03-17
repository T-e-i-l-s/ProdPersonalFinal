package com.example.prodfinal.presentation.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

// Начата ли загрузка данных
var isUserLoading = false

// Данные с экрана "Создать аккаунт"
val randomUser = mutableStateOf(
    UserModel(
        mutableStateOf(""),
        mutableStateOf(""),
        mutableStateOf(""),
        mutableStateOf(""),
        mutableStateOf(""),
        mutableStateOf(""),
    )
)

// Данные с экрана "Войти"
val username = mutableStateOf("")
val password = mutableStateOf("")

@Composable
fun AuthorisationScreen(context: Context, navController: NavController) {
    if (!isUserLoading) {
        isUserLoading = true
        GetRandomUser().getRandomUser(
            context
        ) { response ->
            randomUser.value = response
        }
    }


    val mode = navController.currentBackStackEntry?.arguments?.getString("mode")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
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

        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.background))
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (mode == "LOGIN") "Войти"
                else if (mode == "SIGNIN") "Создать аккаунт"
                else "",
                color = colorResource(id = R.color.text),
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                modifier = Modifier
                    .padding(5.dp)
            )

            if (mode == "LOGIN") {
                LogIn()
            } else if (mode == "SIGNIN") {
                SignIn()
            }

            Button(
                onClick = {
                    if (mode == "LOGIN") {
                        AllowUser().checkAccess(
                            context,
                            username.value,
                            password.value
                        ) {
                            isRegistered.value = it
                        }
                    }  else if (mode == "SIGNIN") {
                        UserInfo().saveUser(context, randomUser.value)
                    }
                    navController.navigate("main_component")
                },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.main),
                    colorResource(id = R.color.main),
                    colorResource(id = R.color.selected),
                    colorResource(id = R.color.selected),
                ),
            ) {
                Text(
                    text = "Далее",
                    color = colorResource(id = R.color.text),
                    fontSize = 16.sp,
                )
            }
        }
    }
}


@Composable
fun LogIn() {
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            },
            label = { Text("Имя") }
        )
    }
    Column(
        modifier = Modifier.padding(5.dp)
    ) {
        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            label = { Text("Пароль") }
        )
    }
}


@Composable
fun SignIn() {
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TextField(
            value = randomUser.value.name.value,
            onValueChange = {
                randomUser.value.name.value = it
            },
            label = { Text("Имя") }
        )
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TextField(
            value = randomUser.value.mail.value,
            onValueChange = {
                randomUser.value.mail.value = it
            },
            label = { Text("Почта") }
        )
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TextField(
            value = randomUser.value.birthday.value,
            onValueChange = {
                randomUser.value.birthday.value = it
            },
            label = { Text("День рождения") }
        )
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TextField(
            value = randomUser.value.address.value,
            onValueChange = {
                randomUser.value.address.value = it
            },
            label = { Text("Адрес") }
        )
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TextField(
            value = randomUser.value.phone_number.value,
            onValueChange = {
                randomUser.value.phone_number.value = it
            },
            label = { Text("Телефон") }
        )
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TextField(
            value = randomUser.value.password.value,
            onValueChange = {
                randomUser.value.password.value = it
            },
            label = { Text("Пароль") }
        )
    }
}