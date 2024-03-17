package com.example.prodfinal.domain.authorization

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.prodfinal.domain.model.UserModel

class UserInfo {
    fun saveUser (context: Context, user: UserModel) {
        val sharedPref = context.getSharedPreferences("LifestyleHUB",Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("name", user.name.value)
            .putString("mail", user.mail.value)
            .putString("birthday", user.birthday.value)
            .putString("address", user.address.value)
            .putString("phone_number", user.phone_number.value)
            .putString("password", user.password.value)
            .apply()
    }

    fun getUser (context: Context, onFinish: (result: UserModel) -> Unit) {
        val sharedPref = context.getSharedPreferences("LifestyleHUB",Context.MODE_PRIVATE)

        val name = sharedPref.getString("name", null)
        val mail = sharedPref.getString("mail", null)
        val birthday = sharedPref.getString("birthday", null)
        val address = sharedPref.getString("address", null)
        val phoneNum = sharedPref.getString("phone_number", null)
        val password = sharedPref.getString("password", null)

        onFinish(
            UserModel(
                mutableStateOf("" + name),
                mutableStateOf("" + mail),
                mutableStateOf("" + birthday),
                mutableStateOf("" + address),
                mutableStateOf("" + phoneNum),
                mutableStateOf("" + password)
            )
        )
    }
}