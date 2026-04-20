package com.example.myprofilapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val body: String,
    var imageUrl: String = ""
) {
    val author: String = "Humas ITERA"
    val date: String = "25 Mei 2024"
}
