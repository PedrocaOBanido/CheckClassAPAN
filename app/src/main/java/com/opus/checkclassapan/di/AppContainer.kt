package com.opus.checkclassapan.di

import android.content.Context
import com.opus.checkclassapan.data.AppDatabase
import com.opus.checkclassapan.data.repository.ClassRepository
import com.opus.checkclassapan.data.repository.OfflineClassRepository

interface AppContainer {
    val classRepository: ClassRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val classRepository: ClassRepository by lazy {
        OfflineClassRepository(AppDatabase.getDatabase(context).classDao())
    }
}
