package com.opus.checkclassapan.data.repository

import com.opus.checkclassapan.data.dao.ClassDao
import com.opus.checkclassapan.data.model.ClassWithStudents
import kotlinx.coroutines.flow.Flow

class OfflineClassRepository(private val classDao: ClassDao) : ClassRepository {
    override fun getClassesWithStudents(): Flow<List<ClassWithStudents>> = classDao.getClassesWithStudents()
}
