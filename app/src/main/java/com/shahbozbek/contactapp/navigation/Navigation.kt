package com.shahbozbek.contactapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shahbozbek.contactapp.ui.screens.edit.ContactDetailScreen
import com.shahbozbek.contactapp.ui.screens.main.MainScreen
import com.shahbozbek.contactapp.ui.screens.main.MainScreenViewModel
import com.shahbozbek.contactapp.util.ContactApp

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "contactApp") {
        composable("contactApp") {
            ContactApp(navController = navController)
        }
        composable("mainScreen") {
            MainScreen(navController = navController)
        }
        composable(
            "contact_detail_screen/{contactName}/{contactPhone}/{contactImage}",
            arguments = listOf(
                navArgument("contactName") { type = NavType.StringType },
                navArgument("contactPhone") { type = NavType.StringType },
                navArgument("contactImage") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: ""
            val contactPhone = backStackEntry.arguments?.getString("contactPhone") ?: ""
            val contactImage = backStackEntry.arguments?.getString("contactImage")
            ContactDetailScreen(
                navController = navController,
                contactName = contactName,
                contactPhone = contactPhone,
                contactImage = contactImage
            )
        }
    }
}