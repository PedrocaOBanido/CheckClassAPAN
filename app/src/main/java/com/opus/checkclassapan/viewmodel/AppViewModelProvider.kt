package com.opus.checkclassapan.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.opus.checkclassapan.CheckClassApanApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AttendanceViewModel(
                checkClassApanApplication().container.classRepository
            )
        }
    }
}

fun CreationExtras.checkClassApanApplication(): CheckClassApanApplication = 
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CheckClassApanApplication)
