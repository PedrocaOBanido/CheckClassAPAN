package com.opus.checkclassapan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.opus.checkclassapan.data.model.Student
import com.opus.checkclassapan.data.repository.ClassRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AttendanceViewModel(classRepository: ClassRepository) : ViewModel() {

    val attendanceUiState: StateFlow<AttendanceUiState> = 
        classRepository.getClassesWithStudents().map { classesWithStudents ->
            if (classesWithStudents.isNotEmpty()) {
                AttendanceUiState(classesWithStudents[0].students)
            } else {
                AttendanceUiState()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = AttendanceUiState()
        )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class AttendanceUiState(
    val students: List<Student> = listOf()
)
