package com.opus.checkclassapan.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ClassWithStudents(
    @Embedded val schoolClass: SchoolClass,
    @Relation(
        parentColumn = "id",
        entityColumn = "classId"
    )
    val students: List<Student>
)
