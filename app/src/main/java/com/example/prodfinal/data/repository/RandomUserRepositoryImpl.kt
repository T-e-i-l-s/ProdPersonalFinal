package com.example.prodfinal.data.repository

import android.content.Context
import com.example.prodfinal.data.api.RandomUserApi
import com.example.prodfinal.domain.model.UserModel

class RandomUserRepositoryImpl {
    fun getRandomUser (
        context: Context,
        onFinish: (result: UserModel) -> Unit
    ) {
        RandomUserApi().getRandomUser(context, onFinish)
    }
}