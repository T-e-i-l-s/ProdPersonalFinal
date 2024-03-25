package com.example.prodfinal.presentation.style

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.prodfinal.R

// Единый стиль для TextField

@Composable
fun getWhiteButtonColors() : ButtonColors =
    ButtonDefaults.buttonColors(
        colorResource(id = R.color.main),
        colorResource(id = R.color.main),
        colorResource(id = R.color.selected),
        colorResource(id = R.color.selected),
    )