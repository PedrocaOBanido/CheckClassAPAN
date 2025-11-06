package com.opus.checkclassapan

import android.app.Application
import com.opus.checkclassapan.di.AppContainer
import com.opus.checkclassapan.di.AppDataContainer

class CheckClassApanApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
