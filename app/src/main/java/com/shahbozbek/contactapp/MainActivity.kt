package com.shahbozbek.contactapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.shahbozbek.contactapp.navigation.MyNavigation
import com.shahbozbek.contactapp.ui.screens.main.MainScreenViewModel
import com.shahbozbek.contactapp.ui.theme.ContactAppTheme
import com.shahbozbek.contactapp.util.Constants.REQUEST_CALL_PERMISSION_CODE
import com.shahbozbek.contactapp.util.Constants.REQUEST_LOCATION_PERMISSION_CODE
import com.shahbozbek.contactapp.util.Constants.REQUEST_SMS_PERMISSION_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavigation()
                }
            }
        }
    }
    @Deprecated(
        "This method has been deprecated in favor of using the Activity Result API\n      " +
                "which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      " +
                "contracts for common intents available in\n      " +
                "{@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      " +
                "testing, and allow receiving results in separate, testable classes independent from your\n      " +
                "activity. Use\n      " +
                "{@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      " +
                "in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      " +
                "handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}."
    )
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CALL_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Call permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Call permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            REQUEST_SMS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            REQUEST_LOCATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
