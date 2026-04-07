package com.example.myprofilapp

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)
