package com.opus.checkclassapan.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Student(
    val id: String,
    val name: String,
    initialIsPresent: Boolean = false
) {
    var isPresent by mutableStateOf(initialIsPresent)
}
