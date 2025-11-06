package com.opus.checkclassapan.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SchoolClass(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
