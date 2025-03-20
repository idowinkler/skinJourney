package com.example.skinJourney.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    val email: String,
    var nickname: String,
    var imageUrl: String
) {
    constructor() : this("", "", "", "")
}