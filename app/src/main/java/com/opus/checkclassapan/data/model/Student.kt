package com.opus.checkclassapan.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = SchoolClass::class, parentColumns = ["id"], childColumns = ["classId"], onDelete = ForeignKey.CASCADE)])
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val classId: Int
)
