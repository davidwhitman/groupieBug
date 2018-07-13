package com.example.dwhitman.playground

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager

/**
 * TODO Add class description.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, Configuration.Builder().build())
    }
}