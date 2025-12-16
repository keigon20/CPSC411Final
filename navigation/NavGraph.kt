package com.example.cpsc411final.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cpsc411final.ui.screens.auth.LoginScreen
import com.example.cpsc411final.ui.screens.auth.SignUpScreen
import com.example.cpsc411final.ui.screens.main.AddEditScreen
import com.example.cpsc411final.ui.screens.main.DetailScreen
import com.example.cpsc411final.ui.screens.main.ListScreen
import com.example.cpsc411final.ui.screens.main.ProfileScreen
import com.example.cpsc411final.viewmodel.AuthViewModel
import com.example.cpsc411final.viewmodel.ItemsViewModel

@Composable
fun AppNavGraph(
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel,
    itemsViewModel: ItemsViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = if (isLoggedIn) "list" else "login") {
        composable("login") {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("signup") {
            SignUpScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("list") {
            ListScreen(navController = navController, itemsViewModel = itemsViewModel)
        }
        composable("profile") {
            ProfileScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(
            route = "add_edit?itemId={itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            AddEditScreen(
                navController = navController,
                itemsViewModel = itemsViewModel,
                itemId = it.arguments?.getString("itemId")
            )
        }
        composable(
            route = "detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) {
            DetailScreen(
                navController = navController,
                itemsViewModel = itemsViewModel,
                itemId = it.arguments?.getString("itemId")!!
            )
        }
    }
}