package com.example.cpsc411final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cpsc411final.navigation.AppNavGraph
import com.example.cpsc411final.ui.theme.CPSC411FinalTheme
import com.example.cpsc411final.viewmodel.AuthViewModel
import com.example.cpsc411final.viewmodel.ItemsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CPSC411FinalTheme {
                val authViewModel: AuthViewModel = hiltViewModel()
                val itemsViewModel: ItemsViewModel = hiltViewModel()
                val isLoggedIn by authViewModel.isLoggedIn.collectAsStateWithLifecycle()

                AppNavGraph(
                    isLoggedIn = isLoggedIn,
                    authViewModel = authViewModel,
                    itemsViewModel = itemsViewModel
                )
            }
        }
    }
}