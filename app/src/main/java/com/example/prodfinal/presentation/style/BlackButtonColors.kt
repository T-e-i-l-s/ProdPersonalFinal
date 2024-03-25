package com.example.prodfinal.presentation.style

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.prodfinal.R

// Единый стиль для темных кнопок

@Composable
fun getBlackButtonColors() : ButtonColors =
    ButtonDefaults.buttonColors(
        colorResource(id = R.color.text),
        colorResource(id = R.color.text),
        colorResource(id = R.color.text),
        colorResource(id = R.color.text),
    )