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

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isEditing = itemId != null

    // This effect runs when the screen is first displayed or when itemId/items change.
    // It finds the item to edit and populates the text fields.
    LaunchedEffect(itemId, items) {
        if (isEditing) {
            val item = items.find { it.documentId == itemId }
            if (item != null) {
                itemsViewModel.selectItem(item)
                title = item.title
                description = item.description
            }
        }
    }

    // This effect cleans up the ViewModel state when the user leaves the screen.
    DisposableEffect(Unit) {
        onDispose {
            itemsViewModel.clearSelectedItem()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Item" else "Add Item") }
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
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (isEditing) {
                        // Call the correct update function from the ViewModel
                        itemsViewModel.updateSelectedItem(title, description)
                    } else {
                        // Call the correct create function from the ViewModel
                        itemsViewModel.createItem(title, description)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                // Disable the button while the item data is loading
                enabled = if (isEditing) selectedItem != null else true
            ) {
                Text(if (isEditing) "Update" else "Add")
            }
        }
    }
}