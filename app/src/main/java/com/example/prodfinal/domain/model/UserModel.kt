package com.example.prodfinal.domain.model

import androidx.compose.runtime.MutableState

data class UserModel(
    var name: MutableState<String>,
    var mail: MutableState<String>,
    var birthday: MutableState<String>,
    var address: MutableState<String>,
    var phone_number: MutableState<String>,
    var password: MutableState<String>,
)
