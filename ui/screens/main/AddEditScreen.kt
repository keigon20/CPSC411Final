package com.example.cpsc411final.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cpsc411final.viewmodel.ItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    navController: NavController,
    itemsViewModel: ItemsViewModel,
    itemId: String?
) {
    val items by itemsViewModel.items.collectAsStateWithLifecycle()
    val selectedItem by itemsViewModel.selectedItem.collectAsStateWithLifecycle()

    var course by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }

    val isEditing = itemId != null

    LaunchedEffect(itemId, items) {
        if (isEditing) {
            val item = items.find { it.documentId == itemId }
            if (item != null) {
                itemsViewModel.selectItem(item)
                course = item.course
                score = item.score
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            itemsViewModel.clearSelectedItem()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Round" else "Add Round") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = course,
                onValueChange = { course = it },
                label = { Text("Course") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = score,
                onValueChange = { score = it },
                label = { Text("Score") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (isEditing) {
                        itemsViewModel.updateSelectedItem(course, score)
                    } else {
                        itemsViewModel.createItem(course, score)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = if (isEditing) selectedItem != null else true
            ) {
                Text(if (isEditing) "Update" else "Add")
            }
        }
    }
}