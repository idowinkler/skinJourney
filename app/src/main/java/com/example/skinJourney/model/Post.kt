package com.example.skinJourney.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Post(
    @PrimaryKey val uid: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val aiAnalysis: String = "",
    val userId: String = "",
    val timestamp: Long = System.currentTimeMillis() // ✅ Store timestamp for sorting
) : Parcelable
