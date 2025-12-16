package com.example.cpsc411final.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cpsc411final.viewmodel.AuthViewModel

@Composable
fun HomeScreen(onNavigateToList: () -> Unit, authViewModel: AuthViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Dashboard", style = MaterialTheme.typography.headlineSmall)
            IconButton(onClick = { authViewModel.signOut() }) {
                Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = "Sign out")
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onNavigateToList) { Text("My Items") }
    }
}
