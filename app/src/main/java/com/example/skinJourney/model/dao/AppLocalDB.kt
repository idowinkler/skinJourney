package com.example.skinJourney.model.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skinJourney.base.MyApplication
import com.example.skinJourney.database.UserDao
import com.example.skinJourney.model.Post
import com.example.skinJourney.model.User
import com.example.skinJourney.repository.PostDao

@Database(entities = [User::class, Post::class], version = 5)
abstract class AppLocalDbRepository: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
}

object AppLocalDB {
    val db: AppLocalDbRepository by lazy {
        val context = MyApplication.Globals.context
            ?: throw IllegalStateException("Application context not available")
        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }
}