package com.project.storyapps.model

data class ListStory(
    val id: String,
    val name: String,
    val description: String,
    val photoURL: String,
    val createdAt: String,
    val lat: Double,
    val lon: Double
)
