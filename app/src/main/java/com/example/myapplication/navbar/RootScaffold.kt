package com.example.myapplication.navbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun RootScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                navController = navController,
                onProfileClick = { navController.navigate("profile") },
                onHomeClick = { navController.navigate("home") },
                onDiscoverClick = { navController.navigate("discover") }
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
