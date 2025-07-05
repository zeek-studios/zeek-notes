package com.zeekstudios.zeeknotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zeekstudios.zeeknotes.ui.screens.home.HomeScreen
import com.zeekstudios.zeeknotes.ui.screens.note.NoteScreen
import com.zeekstudios.zeeknotes.ui.screens.onboarding.OnboardingScreen

@Composable
fun ZeekNotesNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                onNavigateToNote = { noteType ->
                    navController.navigate("note/$noteType")
                }
            )
        }
        
        composable("note/{noteType}") { backStackEntry ->
            val noteType = backStackEntry.arguments?.getString("noteType") ?: "quick"
            NoteScreen(
                noteType = noteType,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}