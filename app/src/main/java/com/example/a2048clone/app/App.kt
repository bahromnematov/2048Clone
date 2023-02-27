package com.example.a2048clone.app

import android.app.Application
import android.content.SharedPreferences
import com.example.a2048clone.shared.AppPref

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPref.getInstance(this)

    }
}