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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.opus.checkclassapan.ui.composables.CheckClassFloatingActionButton
import com.opus.checkclassapan.ui.composables.CheckClassTopAppBar
import com.opus.checkclassapan.ui.theme.CheckClassApanTheme
import com.opus.checkclassapan.viewmodel.AppViewModelProvider
import com.opus.checkclassapan.viewmodel.AttendanceViewModel

@Composable
fun AttendanceView(attendanceViewModel: AttendanceViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val uiState by attendanceViewModel.uiState.collectAsState()

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
            if (uiState.classWithStudents.isNotEmpty()) {
                val firstClass = uiState.classWithStudents[0]
                Text(text = "Data: 05/11/2025")
                Text(text = "Turma: ${firstClass.schoolClass.name}")

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { attendanceViewModel.toggleAllStudents(true) }) {
                        Text(text = "Marcar todos")
                    }
                    Button(onClick = { attendanceViewModel.toggleAllStudents(false) }) {
                        Text(text = "Desmarcar todos")
                    }
                }

                LazyColumn {
                    itemsIndexed(firstClass.students) { index, student ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = uiState.studentPresence[student.id] ?: true,
                                onCheckedChange = { isChecked ->
                                    attendanceViewModel.toggleStudent(student.id, isChecked)
                                }
                            )
                            Text(
                                text = "${student.name} - ${student.id}",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            } else {
                Text("No classes found.")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AttendanceViewPreview() {
    CheckClassApanTheme {
        AttendanceView()
    }
}
