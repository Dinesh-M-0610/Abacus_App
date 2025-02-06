package com.example.abacusapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.abacusapp.ui.theme.AbacusAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val levelViewModel: LevelViewModel = viewModel()
            val authViewModel: AuthViewModel = viewModel()
            val dashboardViewModel: DashboardViewModel = viewModel()
            val adminViewModel: AdminViewModel = viewModel()
            AbacusAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    StudentLevelsApp(
                        navController = navController,
                        authViewModel = authViewModel,
                        dashboardViewModel = dashboardViewModel,
                        levelViewModel = levelViewModel,
                        adminViewModel = adminViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun StudentLevelsApp(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    dashboardViewModel: DashboardViewModel,
    levelViewModel: LevelViewModel,
    adminViewModel: AdminViewModel
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    var startDestination by remember { mutableStateOf("auth") }
    startDestination = if (isLoggedIn) "dashboard" else "auth"
    NavHost(navController = navController, startDestination = startDestination) {
        composable("admin_auth") {
            AdminAuthScreen(
                onAuthSuccess = { navController.navigate("admin_students") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("admin_students") {
            AdminStudentListScreen(
                viewModel = adminViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("auth") {
            AuthScreen(
                authViewModel = authViewModel,
                onAdminClick = { navController.navigate("admin_auth") },
                onLoginSuccess = {
                    navController.navigate("dashboard")
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                dashboardViewModel = dashboardViewModel,
                onLevelSelected = { levelId ->
                    navController.navigate("level/$levelId")
                },
                onLogout = {
                    navController.navigate("auth") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
        composable("level/{levelId}") { backStackEntry ->
            val levelId = backStackEntry.arguments?.getString("levelId")?.toIntOrNull() ?: 1
            LevelScreen(
                levelId = levelId,
                onBackPressed = { navController.popBackStack() },
                levelViewModel = levelViewModel
            )
        }
    }
}




