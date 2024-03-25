package com.example.prodfinal.domain.model

import com.example.prodfinal.domain.state.ToDoState

data class ToDoItemModel(
    val mode: ToDoState,
    val isImportant: Boolean,
    val name: String,
    val description: String,
    val date: String,
    val placeId: String,
    val placeName: String
)
