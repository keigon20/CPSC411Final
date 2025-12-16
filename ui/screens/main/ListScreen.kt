package com.example.cpsc411final.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cpsc411final.data.model.Item
import com.example.cpsc411final.viewmodel.ItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    itemsViewModel: ItemsViewModel
) {
    val items by itemsViewModel.items.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf<Item?>(null) }
    var sortByScore by remember { mutableStateOf(false) }

    val sortedItems = remember(items, sortByScore) {
        if (sortByScore) {
            // Sort by score ascending, treating non-numeric scores as high values
            items.sortedBy { it.score.toIntOrNull() ?: Int.MAX_VALUE }
        } else {
            // Default sort: newest first
            items.sortedByDescending { it.createdAt }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Golf Rounds") },
                actions = {
                    IconButton(onClick = { sortByScore = !sortByScore }) {
                        Icon(Icons.Default.Sort, contentDescription = "Sort by Score")
                    }
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_edit") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Round")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No rounds yet. Tap the + button to add one.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(sortedItems, key = { it.documentId }) { item ->
                        ListItem(
                            headlineContent = { Text(item.course) },
                            supportingContent = { Text("Score: ${item.score}") },
                            modifier = Modifier.clickable {
                                navController.navigate("detail/${item.documentId}")
                            },
                            trailingContent = {
                                IconButton(onClick = { showDeleteDialog = item }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        )
                    }
                }
            }

            showDeleteDialog?.let { itemToDelete ->
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = null },
                    title = { Text("Delete Round") },
                    text = { Text("Are you sure you want to delete the round at '${itemToDelete.course}'?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                itemsViewModel.deleteItem(itemToDelete.documentId)
                                showDeleteDialog = null
                            }
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDeleteDialog = null }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}