package com.example.prodfinal.domain.model

data class FullRecomendationModel(
    val id: String,
    val title: String,
//    val description: String,
    val photos: ArrayList<String>,
    val address: String,
    val category: ArrayList<String>,
    val mail: String?,
)
