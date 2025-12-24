package com.example.cpsc411final.ui.screens.course

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cpsc411final.data.model.Course
import com.example.cpsc411final.viewmodel.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCourseScreen(
    viewModel: CourseViewModel,
    courseId: String?,
    onSaveSuccess: () -> Unit,
    onBack: () -> Unit
) {
    // Basic state for the form
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var par by remember { mutableStateOf("72") }

    // Logic to load course data if editing (simplified for now)
    LaunchedEffect(courseId) {
        if (courseId != null) {
            val course = viewModel.courses.value.find { it.documentId == courseId }
            course?.let {
                name = it.name
                location = it.location
                par = it.par.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (courseId == null) "Add Course" else "Edit Course") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Course Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = par, onValueChange = { par = it }, label = { Text("Par") }, modifier = Modifier.fillMaxWidth())

            Button(
                onClick = {
                    viewModel.saveCourse(Course(documentId = courseId ?: "", name = name, location = location, par = par.toIntOrNull() ?: 72))
                    onSaveSuccess()
                },
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
            ) {
                Text("Save Course")
            }
        }
    }
}
