package com.example.prodfinal.domain.model

data class RecomendationModel(
    val id: String,
    val title: String,
    val image: String,
    val address: String,
    val category: ArrayList<String>,
)
