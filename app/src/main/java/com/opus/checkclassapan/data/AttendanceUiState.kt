package com.opus.checkclassapan.data

import com.opus.checkclassapan.viewmodel.mockStudents

data class AttendanceUiState(
    val className: String = "APAN 3100 • Data Systems",
    val classDetails: String = "Period 2 • Room 204",
    val date: String = "Oct 29",
    val students: List<Student> = mockStudents,
    val attendance: Map<String, AttendanceStatus> = mockStudents.associate { it.id to AttendanceStatus.Present },
    val teacherNotes: String = ""
)
