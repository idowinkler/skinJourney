package com.example.skinJourney.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skinJourney.model.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM Post WHERE userId = :userId")
    fun getUserPosts(userId: String): LiveData<List<Post>>

    @Query("SELECT * FROM Post WHERE userId != :userId")
    fun getOtherUsersPosts(userId: String): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)
}
