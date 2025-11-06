package com.opus.checkclassapan.data.repository

import com.opus.checkclassapan.data.model.ClassWithStudents
import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    fun getClassesWithStudents(): Flow<List<ClassWithStudents>>
}
