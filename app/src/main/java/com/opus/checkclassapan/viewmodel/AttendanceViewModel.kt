package com.opus.checkclassapan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.opus.checkclassapan.data.model.SchoolClass
import com.opus.checkclassapan.data.model.Student
import com.opus.checkclassapan.data.repository.ClassRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AttendanceViewModel(private val classRepository: ClassRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState: StateFlow<AttendanceUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            classRepository.getClassesWithStudents().collect { classesWithStudents ->
                if (classesWithStudents.isNotEmpty()) {
                    val firstClass = classesWithStudents[0]
                    val students = firstClass.students
                    val presenceMap = students.associate { it.id to true }
                    _uiState.value = AttendanceUiState(firstClass.schoolClass, students, presenceMap)
                } else {
                    _uiState.value = AttendanceUiState()
                }
            }
        }
    }

    fun toggleStudent(studentId: String, isPresent: Boolean) {
        _uiState.update { currentState ->
            val newPresence = currentState.studentPresence.toMutableMap()
            newPresence[studentId] = isPresent
            currentState.copy(studentPresence = newPresence)
        }
    }

    fun toggleAllStudents(isPresent: Boolean) {
        _uiState.update { currentState ->
            val newPresence = currentState.students.associate { it.id to isPresent }
            currentState.copy(studentPresence = newPresence)
        }
    }
}

data class AttendanceUiState(
    val schoolClass: SchoolClass? = null,
    val students: List<Student> = emptyList(),
    val studentPresence: Map<String, Boolean> = emptyMap()
)
