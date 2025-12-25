package com.example.cpsc411final.ui.screens.course

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cpsc411final.viewmodel.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseListScreen(
    viewModel: CourseViewModel,
    onAddCourse: () -> Unit,
    onEditCourse: (String) -> Unit,
    onBack: () -> Unit
) {
    val courses by viewModel.courses.collectAsStateWithLifecycle()

    var courseToDelete by remember {mutableStateOf<com.example.cpsc411final.data.model.Course?>(null)}

    if (courseToDelete != null) {
        AlertDialog(
            onDismissRequest = { courseToDelete = null },
            title = { Text("Delete Course") },
            text = { Text("Are you sure you want to delete ${courseToDelete?.name}?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteCourse(courseToDelete!!.documentId)
                    courseToDelete = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { courseToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Golf Courses") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCourse) {
                Icon(Icons.Default.Add, contentDescription = "Add Course")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(courses) { course ->
                ListItem(
                    headlineContent = { Text(course.name) },
                    supportingContent = { Text(course.location) },
                    trailingContent = {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Text("Par ${course.par}", modifier = Modifier.padding(end = 8.dp))

                            IconButton(onClick = { courseToDelete = course }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Course",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        } // This closes the Row
                    }, // This closes the trailingContent block
                    modifier = Modifier.clickable { onEditCourse(course.documentId) }
                )
                HorizontalDivider()
            }
        }
    }
}
