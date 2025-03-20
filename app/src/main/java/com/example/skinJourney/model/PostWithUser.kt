package com.example.skinJourney.model

data class PostWithUser(
    val id: String,
    val description: String,
    val imageUrl: String,
    val userId: String,
    val aiAnalysis: String,
    val userName: String,
    val userImageUrl: String,
    val timestamp: Long
)