package com.opus.checkclassapan.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.opus.checkclassapan.ui.composables.CheckClassFloatingActionButton
import com.opus.checkclassapan.ui.composables.CheckClassTopAppBar
import com.opus.checkclassapan.viewmodel.AttendanceViewModel

@Composable
fun AttendanceView(attendanceViewModel: AttendanceViewModel = viewModel()) {
    val students = attendanceViewModel.students
    var newStudentName by remember { mutableStateOf("") }

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
                icon = Icons.Default.Add,
                onClick = {
                    if (newStudentName.isNotBlank()) {
                        attendanceViewModel.addStudent(newStudentName)
                        newStudentName = ""
                    }
                }
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
                    students.forEach { it.isPresent = true }
                }) {
                    Text(text = "Marcar todos")
                }
                Button(onClick = {
                    students.forEach { it.isPresent = false }
                }) {
                    Text(text = "Desmarcar todos")
                }
            }

            OutlinedTextField(
                value = newStudentName,
                onValueChange = { newStudentName = it },
                label = { Text("Novo aluno") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            LazyColumn {
                items(students) { student ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = student.isPresent,
                            onCheckedChange = { isChecked ->
                                student.isPresent = isChecked
                            }
                        )
                        Text(
                            text = student.name,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        )
                        IconButton(onClick = { attendanceViewModel.removeStudent(student) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remover Aluno")
                        }
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
