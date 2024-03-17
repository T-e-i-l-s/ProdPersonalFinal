package com.example.prodfinal.domain.authorization

import android.content.Context
import com.example.prodfinal.domain.model.UserModel

class AllowUser() {
    fun checkAccess(
        context: Context,
        username: String,
        password: String,
        onFinish: (Boolean) -> Unit
    ) {
        UserInfo().getUser(context) {
            if (it.name.value == username &&
                it.password.value == password
            ) {
                onFinish(true)
            } else {
                onFinish(false)
            }
        }
    }
}