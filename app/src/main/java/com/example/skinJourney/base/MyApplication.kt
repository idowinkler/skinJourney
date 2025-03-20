package com.example.skinJourney.base

import android.app.Application
import android.content.Context
import com.example.skinJourney.model.CloudinaryModel

class MyApplication: Application() {

    object Globals {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        CloudinaryModel.initialize(this)
        Globals.context = applicationContext
    }
}