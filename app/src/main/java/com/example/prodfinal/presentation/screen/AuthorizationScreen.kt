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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodfinal.R
import com.example.prodfinal.data.source.CurrentUserSource
import com.example.prodfinal.data.repository.RandomUserRepositoryImpl
import com.example.prodfinal.domain.authorization.Authorization
import com.example.prodfinal.domain.authorization.Hash
import com.example.prodfinal.domain.model.UserModel
import com.example.prodfinal.domain.state.AuthState
import com.example.prodfinal.navigation.stackCurrentRoute
import com.example.prodfinal.presentation.style.getBlackButtonColors
import com.example.prodfinal.presentation.style.getTextFieldColors

// Данные с экрана "Создать аккаунт"
private val signinUsername = mutableStateOf("")
private val signinPhoto = mutableStateOf("")
private val signinMail = mutableStateOf("")
private val signinAddress = mutableStateOf("")
private val signinPhone = mutableStateOf("")
private val signinBirthday = mutableStateOf("")
private val signinPassword = mutableStateOf("")

// Данные с экрана "Войти"
private val loginUsername = mutableStateOf("")
private val loginPassword = mutableStateOf("")
private val isCorrect = mutableStateOf(true)

// Функция, которая обрабатывает введенные данные и выполняет необходимые действия
fun authorize(context: Context, navController: NavController, mode: AuthState) {
    if (mode == AuthState.LOGIN) { // Вход
        // Проверяем корректность введеных данных
        Authorization().checkAccess(
            context,
            loginUsername.value,
            Hash().encode(loginPassword.value)
        ) {
            if (it) { // Все введено верно
                navController.navigate("main_component")
            } else { // Пользователь не найден
                isCorrect.value = false
            }
        }
    } else if (mode == AuthState.SIGNIN) { // Регистрация
        // Создаем UserModel из введеных данных
        val userInfo = UserModel(
            signinUsername.value,
            signinPhoto.value,
            signinMail.value,
            signinBirthday.value,
            signinAddress.value,
            signinPhone.value,
            Hash().encode(signinPassword.value),
        )
        // Создаем пользователя и сохраняем
        Authorization().createUser(context, userInfo)
        CurrentUserSource().saveCurrentUser(context, userInfo)
        navController.navigate("main_component")
    }
}

@Composable
fun AuthorisationScreen(context: Context, navController: NavController) {
    // Режим экрана(LOGIN или SIGNIN)
    val mode = remember {
        mutableStateOf(AuthState.LOGIN)
    }

    LaunchedEffect(true) {
        // Получаем режим авторизации(вход или регистрация)
        mode.value = AuthState.valueOf(
            "" + navController.currentBackStackEntry?.arguments?.getString("mode")
        )
        // В зависимости от режима авторизации подготавливаем инпуты
        if (mode.value == AuthState.SIGNIN) {
            // Делаем запрос на получение случайного пользователя
            RandomUserRepositoryImpl().getRandomUser(
                context
            ) { response ->
                // Заполняем поля регистрации полученными данными
                signinUsername.value = response.username
                signinPhoto.value = response.photo
                signinMail.value = response.mail
                signinBirthday.value = response.birthday
                signinAddress.value = response.address
                signinPhone.value = response.phoneNumber
                signinPassword.value = response.password
            }
        } else {
            // Очищаем поля для входа
            loginUsername.value = ""
            loginPassword.value = ""
            isCorrect.value = true
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    if (stackCurrentRoute.value == "authorization_screen") {
                        navController.popBackStack()
                    }
                }
        )

        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.background))
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = when (mode.value) {
                    AuthState.LOGIN -> "Войти"
                    AuthState.SIGNIN -> "Создать аккаунт"
                },
                color = colorResource(id = R.color.text),
                fontSize = 19.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily(Font(R.font.wix_madefor_display))
            )

            // В зависимости от режима авторизации отображаем блок инпутов
            if (mode.value == AuthState.LOGIN) {
                LogIn()
            } else if (mode.value == AuthState.SIGNIN) {
                SignIn()
            }

            Button(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                onClick = { authorize(context, navController, mode.value) },
                colors = getBlackButtonColors(),
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Далее",
                    color = colorResource(id = R.color.background),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.wix_madefor_display))
                )
            }
        }
    }
}

// Блок инпутов в режиме входа
@Composable
fun LogIn() {
    if (!isCorrect.value) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            text = "Неверный логин или пароль",
            color = colorResource(id = R.color.error),
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
            fontFamily = FontFamily(Font(R.font.wix_madefor_display))
        )
    }

    TextField(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        value = loginUsername.value,
        onValueChange = {
            isCorrect.value = true
            loginUsername.value = it
        },
        label = { Text(text = "Логин",fontFamily = FontFamily(Font(R.font.wix_madefor_display))) },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp)
    )

    TextField(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        value = "*".repeat(loginPassword.value.length),
        onValueChange = {
            isCorrect.value = true
            loginPassword.value = it
        },
        label = { Text(text = "Пароль", fontFamily = FontFamily(Font(R.font.wix_madefor_display))) },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )
}

// Блок инпутов в режиме регистрации
@Composable
fun SignIn() {
    TextField(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        value = signinUsername.value,
        onValueChange = { signinUsername.value = it },
        label = { Text(text = "Логин", fontFamily = FontFamily(Font(R.font.wix_madefor_display))) },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )

    TextField(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        value = signinMail.value,
        onValueChange = { signinMail.value = it },
        label = { Text("Почта", fontFamily = FontFamily(Font(R.font.wix_madefor_display))) },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )

    TextField(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        value = signinBirthday.value,
        onValueChange = { signinBirthday.value = it },
        label = { Text(text = "День рождения", fontFamily = FontFamily(Font(R.font.wix_madefor_display))) },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )

    TextField(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        value = signinAddress.value,
        onValueChange = { signinAddress.value = it },
        label = { Text(text = "Адрес", fontFamily = FontFamily(Font(R.font.wix_madefor_display))) },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )

    TextField(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        value = signinPhone.value,
        onValueChange = { signinPhone.value = it },
        label = { Text(text = "Телефон", fontFamily = FontFamily(Font(R.font.wix_madefor_display))) },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )

    TextField(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        value = "*".repeat(signinPassword.value.length),
        onValueChange = { signinPassword.value = it },
        label = { Text(text = "Пароль", fontFamily = FontFamily(Font(R.font.wix_madefor_display))) },
        colors = getTextFieldColors(),
        shape = RoundedCornerShape(16.dp),
    )
}