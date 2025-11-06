package com.opus.checkclassapan.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.opus.checkclassapan.data.model.ClassWithStudents
import com.opus.checkclassapan.data.model.SchoolClass
import com.opus.checkclassapan.data.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    @Transaction
    @Query("SELECT * FROM SchoolClass")
    fun getClassesWithStudents(): Flow<List<ClassWithStudents>>

    @Insert
    suspend fun insertClass(schoolClass: SchoolClass): Long

    @Insert
    suspend fun insertStudents(students: List<Student>)
}
