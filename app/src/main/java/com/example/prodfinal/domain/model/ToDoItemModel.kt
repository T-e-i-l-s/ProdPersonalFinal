package com.example.prodfinal.domain.model

data class ToDoItemModel(
    val mode: String,
    val name: String,
    val description: String,
    val date: String,
    val placeId: String,
    val placeName: String
)
