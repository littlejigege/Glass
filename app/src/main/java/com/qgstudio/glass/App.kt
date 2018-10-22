package com.qgstudio.glass

import android.app.Application
import com.qgstudio.glass.common.model.db.AppDatabase

class App : Application() {
    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDatabase.getInstance(this)
    }
}