package com.shahbozbek.contactapp.util

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.shahbozbek.contactapp.data.ContactEntity
import com.shahbozbek.contactapp.ui.screens.main.MainScreen
import com.shahbozbek.contactapp.ui.screens.main.MainScreenViewModel

@Composable
fun ContactApp(navController: NavController) {
    val context = LocalContext.current
    ContactPermission(navController)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactPermission(navController: NavController) {
    val permissionState = rememberPermissionState(android.Manifest.permission.READ_CONTACTS)
    if (permissionState.status.isGranted) {
        MainScreen(navController)
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PermissionRequestScreen(
                onPermissionGranted = {
//                    val contacts = fetchContacts(context)
//                    ContactsList(contacts = contacts)
                },
                onPermissionDenied = { /* Nima qilishni hal qiling */ }
            )
        }
    }
}

@Composable
fun PermissionRequestScreen(
    onPermissionGranted: @Composable () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val activity = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Kontaktingizni o'qish uchun ruxsat kerak.")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                100
            )
        }) {
            Text("Ruxsat berish")
        }
    }
}

fun groupContactsByInitial(contacts: List<ContactEntity>): Map<Char, List<ContactEntity>> {
    return contacts.groupBy { it.name.firstOrNull()?.uppercaseChar() ?: '#' }
}
