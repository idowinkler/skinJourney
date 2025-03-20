package com.example.skinJourney.model.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skinJourney.base.MyApplication
import com.example.skinJourney.database.UserDao
import com.example.skinJourney.model.Student
import com.example.skinJourney.model.User

@Database(entities = [Student::class, User::class], version = 1)
abstract class AppLocalDbRepository: RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun userDao(): UserDao
}

// NOTE: keeping this unused code. was approved by tal zion on 20/2 zoom meeting
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