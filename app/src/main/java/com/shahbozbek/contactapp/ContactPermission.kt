package com.shahbozbek.contactapp

import android.app.Activity
import android.content.Context
import android.provider.ContactsContract
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.shahbozbek.contactapp.data.ContactEntity

@Composable
fun ContactApp(viewModel: ContactViewModel) {
    val context = LocalContext.current
    ContactPermission(context, viewModel)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactPermission(context: Context, viewModel: ContactViewModel) {
    val permissionState = rememberPermissionState(android.Manifest.permission.READ_CONTACTS)
    if (!permissionState.status.isGranted) {
        Button(onClick = { permissionState.launchPermissionRequest() }) {
            Text("Ruxsat berish")
        }
    } else {
        val contacts = fetchContacts(context)
        ContactsList(contacts = contacts)
    }
    val hasPermission = remember {
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_CONTACTS
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }
    if (hasPermission) {
        val contacts = fetchContacts(context)
        ContactsList(contacts = contacts)
        ContactsScreen(viewModel)
    } else {
        Button(onClick = { permissionState.launchPermissionRequest() }) {
            Text("Ruxsat berish")
        }
    }

    if (permissionState.status.isGranted) {
        val contacts = fetchContacts(context)
        ContactsList(contacts = contacts)
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PermissionRequestScreen(
                onPermissionGranted = {
                    val contacts = fetchContacts(context)
                    ContactsList(contacts = contacts)
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
            Text("Ruxsat so'rash")
        }
    }
}

fun groupContactsByInitial(contacts: List<ContactEntity>): Map<Char, List<ContactEntity>> {
    return contacts.groupBy { it.name.firstOrNull()?.uppercaseChar() ?: '#' }
}

fun fetchContacts(context: Context): List<ContactEntity> {
    val contactList = mutableListOf<ContactEntity>()
    val contentResolver = context.contentResolver
    val cursor = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        ),
        null,
        null,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
    )

    cursor?.use {
        while (it.moveToNext()) {
            val name =
                it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phone =
                it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val photoUri =
                it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
            contactList.add(ContactEntity(name = name, phoneNumber = phone, avatarUrl = photoUri))
        }
    }
    return contactList
}