package com.opus.checkclassapan.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.opus.checkclassapan.data.Student
import com.opus.checkclassapan.data.students as studentList
import com.opus.checkclassapan.ui.composables.CheckClassFloatingActionButton
import com.opus.checkclassapan.ui.composables.CheckClassTopAppBar

// This view refers to AttendancePrototype.png

@Composable
fun AttendanceView() {
    val (students, setStudents) = remember {
        mutableStateOf(
            studentList
        )
    }

    Scaffold(
        topBar = {
            CheckClassTopAppBar(
                title = "Frequência",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = {}
            )
        },
        floatingActionButton = {
            CheckClassFloatingActionButton(
                icon = Icons.Default.Check,
                onClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = "Data: 05/11/2025")
            Text(text = "Turma: 3º ano A")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    setStudents(students.map { it.copy(isPresent = true) })
                }) {
                    Text(text = "Marcar todos")
                }
                Button(onClick = {
                    setStudents(students.map { it.copy(isPresent = false) })
                }) {
                    Text(text = "Desmarcar todos")
                }
            }

            LazyColumn {
                itemsIndexed(students) { index, student ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = student.isPresent,
                            onCheckedChange = { isChecked ->
                                val updatedStudents = students.toMutableList()
                                updatedStudents[index] = student.copy(isPresent = isChecked)
                                setStudents(updatedStudents)
                            }
                        )
                        Text(
                            text = "${student.name} - ${student.id}",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AttendanceViewPreview() {
    AttendanceView()
}
