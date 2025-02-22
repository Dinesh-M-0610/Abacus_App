package com.example.abacusapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminStudentListScreen(
    viewModel: AdminViewModel,
    onBackClick: () -> Unit
) {
    val students by viewModel.students.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var selectedStudent by remember { mutableStateOf<Student?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Students List") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if(isLoading){
            LoadingScreen()
        } else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(students) { student ->
                    StudentCard(
                        student = student,
                        onClick = { selectedStudent = student }
                    )
                }
            }
        }
    }

    selectedStudent?.let { student ->
        StudentActionDialog(
            student = student,
            onDismiss = { selectedStudent = null },
            onAllow = {
                viewModel.allowStudent(student)
                selectedStudent = null
            },
            onUpdate = { updatedStudent ->
                viewModel.updateStudent(updatedStudent)
                selectedStudent = null
            },
            onRemove = {
                viewModel.removeStudent(student)
                selectedStudent = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelPermissionDropdown(
    level: Int,
    isPermitted: Boolean,
    onPermissionChange: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Permitted", "Not Permitted")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = if (isPermitted) "Permitted" else "Not Permitted",
            onValueChange = {},
            readOnly = true,
            label = { Text("Level $level") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onPermissionChange(option == "Permitted")
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun StudentCard(
    student: Student,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = student.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun StudentActionDialog(
    student: Student,
    onDismiss: () -> Unit,
    onAllow: () -> Unit,
    onUpdate: (Student) -> Unit,
    onRemove: () -> Unit
) {
    var updatedStudent by remember { mutableStateOf(student) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Manage Student") },
        text = {
            Column {
                Text("Name: ${student.name}")
                Spacer(modifier = Modifier.height(16.dp))
                Text("Update Permissions:")
                LevelPermissionDropdown(
                    level = 1,
                    isPermitted = updatedStudent.level1,
                    onPermissionChange = { updatedStudent = updatedStudent.copy(level1 = it) }
                )
                LevelPermissionDropdown(
                    level = 2,
                    isPermitted = updatedStudent.level2,
                    onPermissionChange = { updatedStudent = updatedStudent.copy(level2 = it) }
                )
                LevelPermissionDropdown(
                    level = 3,
                    isPermitted = updatedStudent.level3,
                    onPermissionChange = { updatedStudent = updatedStudent.copy(level3 = it) }
                )
                LevelPermissionDropdown(
                    level = 4,
                    isPermitted = updatedStudent.level4,
                    onPermissionChange = { updatedStudent = updatedStudent.copy(level4 = it) }
                )
                LevelPermissionDropdown(
                    level = 5,
                    isPermitted = updatedStudent.level5,
                    onPermissionChange = { updatedStudent = updatedStudent.copy(level5 = it) }
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onUpdate(updatedStudent) }) {
                    Text("Update")
                }
                if (student.access) {
                    Button(
                        onClick = onRemove,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Remove")
                    }
                } else {
                    Button(
                        onClick = onAllow,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Allow")
                    }
                }
            }
        }
    )
}



