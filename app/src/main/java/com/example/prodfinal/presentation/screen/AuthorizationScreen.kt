package com.example.prodfinal.presentation.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.local.SaveCurrentUser
import com.example.prodfinal.domain.authorization.AllowUser
import com.example.prodfinal.domain.authorization.GetRandomUser
import com.example.prodfinal.domain.authorization.UserInfo
import com.example.prodfinal.domain.model.UserModel
import com.example.prodfinal.presentation.style.getTextFieldColors

// Данные с экрана "Создать аккаунт"
private val signinUsername = mutableStateOf("")
private val signinMail = mutableStateOf("")
private val signinAddress = mutableStateOf("")
private val signinPhone = mutableStateOf("")
private val signinBirthday = mutableStateOf("")
private val signinPassword = mutableStateOf("")

// Данные с экрана "Войти"
private val loginUsername = mutableStateOf("")
private val loginPassword = mutableStateOf("")

@Composable
fun AuthorisationScreen(context: Context, navController: NavController) {
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
            signinUsername.value = response.name
            signinMail.value = response.mail
            signinBirthday.value = response.birthday
            signinAddress.value = response.address
            signinPhone.value = response.phone_number
            signinPassword.value = response.password
        }
        loginUsername.value = ""
        loginPassword.value = ""
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
                .clickable(
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
            Column(
                modifier = Modifier
                    .weight(1f, false)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
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
                    LogIn()
                } else if (mode.value == "SIGNIN") {
                    SignIn()
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (mode.value == "LOGIN") {
                            AllowUser().checkAccess(
                                context,
                                loginUsername.value,
                                loginPassword.value
                            )
                        } else if (mode.value == "SIGNIN") {
                            val userInfo = UserModel(
                                signinUsername.value,
                                signinMail.value,
                                signinBirthday.value,
                                signinAddress.value,
                                signinPhone.value,
                                signinPassword.value,
                            )
                            UserInfo().createUser(context, userInfo)
                            SaveCurrentUser().saveCurrentUser(context, userInfo)
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
}

// Блок инпутов в режиме входа
@Composable
fun LogIn() {
    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = loginUsername.value,
            onValueChange = {
                loginUsername.value = it
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
            value = loginPassword.value,
            onValueChange = {
                loginPassword.value = it
            },
            label = { Text("Пароль") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }
}

// Блок инпутов в режиме регистрации
@Composable
fun SignIn() {
    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = signinUsername.value,
            onValueChange = {
                signinUsername.value = it
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
            value = signinMail.value,
            onValueChange = {
                signinMail.value = it
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
            value = signinBirthday.value,
            onValueChange = {
                signinBirthday.value = it
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
            value = signinAddress.value,
            onValueChange = {
                signinAddress.value = it
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
            value = signinPhone.value,
            onValueChange = {
                signinPhone.value = it
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
            value = signinPassword.value,
            onValueChange = {
                signinPassword.value = it
            },
            label = { Text("Пароль") },
            colors = getTextFieldColors(),
            shape = RoundedCornerShape(16.dp),
        )
    }
}