package com.example.mystudentsapplication.model.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mystudentsapplication.base.MyApplication
import com.example.mystudentsapplication.model.Student

@Database(entities = [Student::class], version = 1)
abstract class AppLocalDbRepository: RoomDatabase() {
    abstract fun studentDao(): StudentDao
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