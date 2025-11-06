package com.opus.checkclassapan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.ui.graphics.vector.ImageVector

// --- Data Classes and Enums (for UI state) ---

data class Student(val id: String, val name: String, val imageUrl: String = "")

enum class AttendanceStatus {
    Present, Late, Absent
}

data class AttendanceUiState(
    val className: String = "Turma A",
    val classDetails: String = "Turma APAN | 3º Trimestre 2025",
    val date: String = "06 Nov. 2025",
    val students: List<Student> = emptyList(),
    val attendance: Map<String, AttendanceStatus> = emptyMap(),
    val teacherNotes: String = ""
)

// --- Mock ViewModel ---

class MockAttendanceViewModel {
    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState: StateFlow<AttendanceUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val sampleStudents = listOf(
            Student("1", "John Doe"),
            Student("2", "Jane Smith"),
            Student("3", "Peter Jones"),
            Student("4", "Mary Williams"),
            Student("5", "David Brown")
        )
        _uiState.update { currentState ->
            currentState.copy(
                students = sampleStudents,
                attendance = sampleStudents.associate { it.id to AttendanceStatus.Absent }
            )
        }
    }

    fun updateStudentAttendance(studentId: String, status: AttendanceStatus) {
        _uiState.update { currentState ->
            val updatedAttendance = currentState.attendance.toMutableMap()
            updatedAttendance[studentId] = status
            currentState.copy(attendance = updatedAttendance)
        }
    }

    fun markAllPresent() {
        _uiState.update { currentState ->
            val updatedAttendance = currentState.attendance.mapValues { AttendanceStatus.Present }
            currentState.copy(attendance = updatedAttendance)
        }
    }

    fun updateTeacherNotes(notes: String) {
        _uiState.update { it.copy(teacherNotes = notes) }
    }

    fun saveDraft() {
        println("Draft saved! ${_uiState.value}")
    }

    fun submitAttendance() {
        println("Attendance submitted! ${_uiState.value}")
    }
}


// --- Color Palette (Approximating the dark theme in the image) ---
val DarkBackground = Color(0xFF121212)
val CardBackground = Color(0xFF1E1E1E)
val LightText = Color(0xFFE0E0E0)
val DarkText = Color(0xFF6A6A6A)
val ActionButtonBackground = Color(0xFF333333)

val PresentColor = Color(0xFF4CAF50) // Green
val LateColor = Color(0xFFFF9800)   // Orange
val AbsentColor = Color(0xFFF44336)  // Red

// --- Main Screen Composable ---

@Composable
fun AttendanceView(viewModel: MockAttendanceViewModel = MockAttendanceViewModel(), modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = DarkBackground,
        topBar = { ClassHeader(uiState = uiState, onMarkAllClick = viewModel::markAllPresent) },
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Main content scrollable area
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Takes up remaining space
                    .padding(horizontal = 16.dp)
            ) {
                items(uiState.students) { student ->
                    StudentAttendanceRow(
                        student = student,
                        currentStatus = uiState.attendance[student.id] ?: AttendanceStatus.Absent,
                        onStatusChange = { status ->
                            viewModel.updateStudentAttendance(student.id, status)
                        }
                    )
                    HorizontalDivider(color = CardBackground, thickness = 1.dp)
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    TeacherNotesSection(
                        notes = uiState.teacherNotes,
                        onNotesChange = viewModel::updateTeacherNotes
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Bottom Action Buttons
            BottomActionButtons(
                onSaveDraft = viewModel::saveDraft,
                onSubmit = viewModel::submitAttendance
            )
        }
    }
}

// --- Header Components ---

@Composable
fun ClassHeader(uiState: AttendanceUiState, onMarkAllClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        // Title and Date
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = uiState.className,
                    color = LightText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = uiState.classDetails,
                    color = DarkText,
                    fontSize = 14.sp
                )
            }
            DateButton(date = uiState.date)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons (All students, Status, Mark all)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HeaderActionButton(text = "All students", icon = Icons.Filled.Group)
            HeaderActionButton(text = "Status", icon = Icons.AutoMirrored.Filled.List)
            HeaderActionButton(text = "Mark all", icon = Icons.Filled.DoneAll, onClick = onMarkAllClick)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DateButton(date: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(CardBackground)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.CalendarToday,
            contentDescription = "Date",
            tint = LightText,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = date, color = LightText, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun HeaderActionButton(text: String, icon: ImageVector, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = ActionButtonBackground),
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = LightText,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, color = LightText, fontSize = 14.sp)
    }
}

// --- Student List Item ---

@Composable
fun StudentAttendanceRow(
    student: Student,
    currentStatus: AttendanceStatus,
    onStatusChange: (AttendanceStatus) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar (Mocked with a simple circle for demonstration)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.5f))
        ) {
            // In a real app, you'd use AsyncImage:
            // AsyncImage(model = student.imageUrl, contentDescription = student.name)
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Student Info
        Column(modifier = Modifier.weight(1f)) {
            Text(text = student.name, color = LightText, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Text(text = "ID: ${student.id}", color = DarkText, fontSize = 12.sp)
        }

        // Status Buttons
        StatusButton(
            text = "Present",
            isSelected = currentStatus == AttendanceStatus.Present,
            color = PresentColor,
            onClick = { onStatusChange(AttendanceStatus.Present) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        StatusButton(
            text = "Late",
            isSelected = currentStatus == AttendanceStatus.Late,
            color = LateColor,
            onClick = { onStatusChange(AttendanceStatus.Late) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        StatusButton(
            text = "Absent",
            isSelected = currentStatus == AttendanceStatus.Absent,
            color = AbsentColor,
            onClick = { onStatusChange(AttendanceStatus.Absent) }
        )
    }
}

@Composable
fun StatusButton(
    text: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    val buttonColor = if (isSelected) color else CardBackground
    val textColor = if (isSelected) Color.Black else LightText

    Text(
        text = text,
        color = textColor,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(buttonColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    )
}

// --- Notes and Bottom Bar Components ---

@Composable
fun TeacherNotesSection(notes: String, onNotesChange: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Teacher Notes", color = LightText, fontWeight = FontWeight.SemiBold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = if (isExpanded) "Expanded" else "Collapsed", color = DarkText, fontSize = 12.sp)
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = DarkText
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isExpanded) {
            BasicTextField(
                value = notes,
                onValueChange = onNotesChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkBackground)
                    .border(1.dp, DarkText.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                textStyle = LocalTextStyle.current.copy(color = LightText, fontSize = 14.sp),
                decorationBox = { innerTextField ->
                    if (notes.isEmpty()) {
                        Text("Add context about today's session, excused absences, or reminders.", color = DarkText, fontSize = 14.sp)
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
fun BottomActionButtons(onSaveDraft: () -> Unit, onSubmit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBackground.copy(alpha = 0.9f)) // Slightly transparent overlay
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onSaveDraft,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = ActionButtonBackground),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text("Save Draft", color = LightText, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onSubmit,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)), // Blue Submit button
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text("Submit Attendance", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun BottomNavigationBar() {
    val items = listOf("Home", "Attendance", "Evaluation", "Profile")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.CheckCircle, Icons.Filled.Edit, Icons.Filled.Person)
    val selectedIndex = 1 // Assuming Attendance is the current screen

    NavigationBar(
        containerColor = CardBackground,
        contentColor = LightText,
        modifier = Modifier.height(60.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item,
                        tint = if (isSelected) Color(0xFF1E88E5) else DarkText
                    )
                },
                label = { Text(item, color = if (isSelected) Color(0xFF1E88E5) else DarkText, fontSize = 10.sp) },
                selected = isSelected,
                onClick = { /* Handle navigation click */ },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = CardBackground,
                    selectedIconColor = Color(0xFF1E88E5),
                    selectedTextColor = Color(0xFF1E88E5),
                    unselectedIconColor = DarkText,
                    unselectedTextColor = DarkText
                )
            )
        }
    }
}


// --- Preview ---

@Preview(showBackground = true)
@Composable
fun AttendanceViewPreview() {
    // You'd need to wrap this in your app theme if it sets up specific typography/colors
    // For this example, we'll use Material3 defaults combined with our custom colors
    AttendanceView(viewModel = MockAttendanceViewModel())
}
