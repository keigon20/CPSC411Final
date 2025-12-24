package com.example.cpsc411final.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cpsc411final.ui.screens.auth.LoginScreen
import com.example.cpsc411final.ui.screens.auth.SignUpScreen
import com.example.cpsc411final.ui.screens.main.AddEditScreen
import com.example.cpsc411final.ui.screens.main.DetailScreen
import com.example.cpsc411final.ui.screens.main.ListScreen
import com.example.cpsc411final.ui.screens.main.ProfileScreen
import com.example.cpsc411final.viewmodel.AuthViewModel
import com.example.cpsc411final.viewmodel.CourseViewModel
import com.example.cpsc411final.viewmodel.ItemsViewModel
import com.example.cpsc411final.ui.screens.course.AddEditCourseScreen
import com.example.cpsc411final.ui.screens.course.CourseListScreen

@Composable
fun AppNavGraph(
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel,
    itemsViewModel: ItemsViewModel,
    courseViewModel: CourseViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // CRITICAL FIX: React to login state changes
    // If the user logs in, automatically jump from Login/Signup to List
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("list") {
                // Clear the backstack so user can't "back" into login screen
                popUpTo(0) { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Logic to hide BottomBar on Login/Signup screens
    val showBottomBar = isLoggedIn && currentDestination?.route !in listOf("login", "signup")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    // TAB 1: Rounds
                    NavigationBarItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Rounds") },
                        selected = currentDestination?.hierarchy?.any { it.route == "list" } == true,
                        onClick = {
                            navController.navigate("list") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    // TAB 2: Courses
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.GolfCourse, contentDescription = null) },
                        label = { Text("Courses") },
                        selected = currentDestination?.hierarchy?.any { it.route == "course_list" } == true,
                        onClick = {
                            navController.navigate("course_list") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    // TAB 3: Profile
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Profile") },
                        selected = currentDestination?.hierarchy?.any { it.route == "profile" } == true,
                        onClick = {
                            navController.navigate("profile") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            // Use login as start destination to avoid "flicker".
            // The LaunchedEffect above will handle the skip to "list" ifLoggedIn is true.
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
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
            composable("course_list") {
                CourseListScreen(
                    viewModel = courseViewModel,
                    onAddCourse = { navController.navigate("add_edit_course/new") },
                    onEditCourse = { courseId -> navController.navigate("add_edit_course/$courseId") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "add_edit_course/{courseId}",
                arguments = listOf(navArgument("courseId") { nullable = true })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId")
                AddEditCourseScreen(
                    viewModel = courseViewModel,
                    // If courseId is "new", we pass null to the ViewModel to signify a fresh entry
                    courseId = if (courseId == "new") null else courseId,
                    onSaveSuccess = { navController.popBackStack() },
                    onBack = { navController.popBackStack() }
                )
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
                    itemId = it.arguments?.getString("itemId") ?: ""
                )
            }
        }
    }
}
