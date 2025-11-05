package com.opus.checkclassapan.viewmodel

import com.opus.checkclassapan.data.Student
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AttendanceViewModelTest {

    private lateinit var viewModel: AttendanceViewModel

    @Before
    fun setup() {
        viewModel = AttendanceViewModel()
    }

    @Test
    fun `test initial students`() {
        assertEquals(3, viewModel.students.size)
    }

    @Test
    fun `test add student`() {
        viewModel.addStudent("Novo Aluno")
        assertEquals(4, viewModel.students.size)
        assertEquals("Novo Aluno", viewModel.students.last().name)
    }

    @Test
    fun `test remove student`() {
        val studentToRemove = viewModel.students.first()
        viewModel.removeStudent(studentToRemove)
        assertEquals(2, viewModel.students.size)
    }
}
