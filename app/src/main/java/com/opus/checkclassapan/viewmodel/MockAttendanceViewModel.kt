package com.opus.checkclassapan.viewmodel

import androidx.lifecycle.ViewModel
import com.opus.checkclassapan.data.AttendanceStatus
import com.opus.checkclassapan.data.AttendanceUiState
import com.opus.checkclassapan.data.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MockAttendanceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState: StateFlow<AttendanceUiState> = _uiState.asStateFlow()

    fun updateStudentAttendance(studentId: String, status: AttendanceStatus) {
        _uiState.update { currentState ->
            currentState.copy(
                attendance = currentState.attendance.toMutableMap().apply {
                    this[studentId] = status
                }
            )
        }
    }

    fun updateTeacherNotes(notes: String) {
        _uiState.update { it.copy(teacherNotes = notes) }
    }

    fun markAllPresent() {
        _uiState.update { currentState ->
            val allPresent = currentState.students.associate { it.id to AttendanceStatus.Present }
            currentState.copy(attendance = allPresent)
        }
    }

    // Placeholder for other actions
    fun saveDraft() {}
    fun submitAttendance() {}
}

val mockStudents = listOf(
    Student("S10293", "Ava Patel", "ava_url"),
    Student("S10294", "Liam Chen", "liam_url"),
    Student("S10295", "Sophia Johnson", "sophia_url"),
    Student("S10296", "Noah Garcia", "noah_url"),
    Student("S10297", "Emma Davis", "emma_url"),
    Student("S10298", "Mason Lee", "mason_url"),
)