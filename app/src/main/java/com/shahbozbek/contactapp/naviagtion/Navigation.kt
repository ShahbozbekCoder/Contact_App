package com.shahbozbek.contactapp.naviagtion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shahbozbek.contactapp.ui.screens.ContactDetailScreen
import com.shahbozbek.contactapp.ui.screens.main.MainScreen
import com.shahbozbek.contactapp.ui.screens.main.MainScreenViewModel
import com.shahbozbek.contactapp.util.ContactApp

@Composable
fun MyNavigation(viewModel: MainScreenViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "contactApp") {
        composable("contactApp") {
            ContactApp(viewModel)
        }
        composable("mainScreen") {
            MainScreen(navController = navController, viewModel)
        }
        composable("editScreen") {
            // EditScreen()
            ContactDetailScreen(
                navController = navController,
                contact = null,
                )
        }
    }
}