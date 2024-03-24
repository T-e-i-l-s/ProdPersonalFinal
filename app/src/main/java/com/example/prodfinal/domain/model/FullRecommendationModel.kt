package com.example.prodfinal.domain.model

data class FullRecommendationModel(
    val id: String,
    val title: String,
    val photos: ArrayList<String>,
    val address: String,
    val category: ArrayList<String>,
    val mail: String?,
)
