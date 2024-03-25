package com.example.prodfinal.presentation.style

import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.prodfinal.R

// Единый стиль для TextField

@Composable
fun getCheckBoxColors() : CheckboxColors =
    CheckboxDefaults.colors(
        colorResource(id = R.color.text),
        colorResource(id = R.color.text),
        colorResource(id = R.color.main),
        colorResource(id = R.color.text),
        colorResource(id = R.color.text),
        colorResource(id = R.color.main),
    )