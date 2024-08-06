package com.example.myapplication.navbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screens.AllSongsScreen
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.ProfileScreen
import com.example.myapplication.viewmodel.TracksViewModel

@Composable
fun NavHostComponent(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: TracksViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        Modifier.padding(innerPadding)
    ) {
        composable("profile") { ProfileScreen() }
        composable("home") { HomeScreen(navController) }
        composable("discover") { AllSongsScreen(navController, viewModel) } // Pass the viewModel here
    }
}
