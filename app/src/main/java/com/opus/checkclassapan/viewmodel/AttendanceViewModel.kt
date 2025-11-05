package com.opus.checkclassapan.viewmodel

import androidx.lifecycle.ViewModel
import com.opus.checkclassapan.data.Student
import java.util.UUID
import androidx.compose.runtime.mutableStateListOf

class AttendanceViewModel : ViewModel() {
    private val _students = mutableStateListOf(
        Student("123", "Fulano de Tal"),
        Student("456", "Ciclano de Tal"),
        Student("789", "Beltrano de Tal")
    )
    val students: List<Student> = _students

    fun addStudent(name: String) {
        val id = UUID.randomUUID().toString()
        _students.add(Student(id, name))
    }

    fun removeStudent(student: Student) {
        _students.remove(student)
    }
}
