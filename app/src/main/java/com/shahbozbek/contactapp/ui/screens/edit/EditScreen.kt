package com.shahbozbek.contactapp.ui.screens.edit

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shahbozbek.contactapp.R
import com.shahbozbek.contactapp.util.Constants.REQUEST_CALL_PERMISSION_CODE
import com.shahbozbek.contactapp.util.Constants.REQUEST_LOCATION_PERMISSION_CODE
import com.shahbozbek.contactapp.util.Constants.REQUEST_SMS_PERMISSION_CODE

@Composable
fun ContactDetailScreen(
    navController: NavController,
    contactName: String,
    contactPhone: String,
    contactImage: String?
//    onEditClick: () -> Unit,
//    onBackClick: () -> Unit,
//    onCallClick: (String) -> Unit,
//    onMessageClick: (String) -> Unit,
//    onVideoCallClick: (String) -> Unit,
//    onShareLocationClick: () -> Unit,
//    onQrCodeClick: () -> Unit,
//    onShareContactClick: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            IconButton(onClick = {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color(0xFF1E88E5)
                )
            }
        }

        // Contact Avatar va Name
        if (contactImage != null) {
            Image(
                painter = rememberAsyncImagePainter(contactImage),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.Gray)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center,
                content = {
                    Text(text = contactName.take(1), fontSize = 32.sp, color = Color.White)
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = contactName, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = contactPhone, color = Color.Gray)


        // Main Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF00BA88))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { sendMessage(context, contactPhone) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = "Message",
                        tint = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF468DFF))
                    .padding(8.dp),
            ) {
                IconButton(onClick = { makeCall(context, contactPhone) }) {
                    Icon(
                        imageVector = Icons.Default.Call, contentDescription = "Call",
                        tint = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF55858))
                    .padding(8.dp),
            ) {
                IconButton(onClick = { startVideoCall(context, contactPhone) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.video_call),
                        contentDescription = "Video Call",
                        tint = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF9E9E9E))
                    .padding(8.dp),
            ) {
                IconButton(onClick = {
                    Toast.makeText(context, "Email send", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Default.Email, contentDescription = "Email",
                        tint = Color.White
                    )
                }
            }

        }
        HorizontalDivider(
            thickness = 32.dp,
            color = Color(0xFFEFEFEF),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Contact Info
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ContactInfoRow(
                    label = "Mobile",
                    value = contactPhone,
                    onCallClick = { makeCall(context, contactPhone) },
                    onMessageClick = { sendMessage(context, contactPhone) }
                )
            }
        }

        // Additional Actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF443DF6))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { shareLocation(context) }) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Share Location",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Share Location", color = Color.Gray, fontSize = 12.sp)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { showQrCode(context) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.qr_code),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(30.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "QR Code", color = Color.Gray, fontSize = 12.sp)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF32EAB9))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {
                        shareContact(
                            context,
                            contactName = contactName,
                            contactPhone = contactPhone
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Contact",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Share Contact", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ContactInfoRow(
    label: String,
    value: String,
    onCallClick: (String) -> Unit,
    onMessageClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontSize = 18.sp, color = Color.Black)
        }
        Row {
            IconButton(onClick = { onCallClick(value) }) {
                Icon(
                    imageVector = Icons.Default.Call, contentDescription = "Call",
                    tint = Color(0xFF4CAF50)
                )
            }
            IconButton(onClick = { onMessageClick(value) }) {
                Icon(
                    painter = painterResource(id = R.drawable.message),
                    contentDescription = "Message",
                    tint = Color(0xFF1E88E5)
                )
            }
        }
    }
}


fun makeCall(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
        context.startActivity(intent)
    } else {
        requestCallPermission(context)
    }
}

fun requestCallPermission(context: Context) {
    if (context is Activity) {
        ActivityCompat.requestPermissions(
            context,
            arrayOf(Manifest.permission.CALL_PHONE),
            REQUEST_CALL_PERMISSION_CODE
        )
    }
}

fun sendMessage(context: Context, phoneNumber: String) {
    if (context.checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:$phoneNumber")
        }
        context.startActivity(intent)
    } else {
        requestSmsPermission(context)
    }
}

fun requestSmsPermission(context: Context) {
    if (context is Activity) {
        ActivityCompat.requestPermissions(
            context,
            arrayOf(Manifest.permission.SEND_SMS),
            REQUEST_SMS_PERMISSION_CODE
        )
    }
}

fun startVideoCall(context: Context, phoneNumber: String) {
    // TODO: O'zingizning video qo'ng'iroq funksiyasiga mos API ishlating
    Toast.makeText(context, "Video Call to $phoneNumber", Toast.LENGTH_SHORT).show()
}

fun shareLocation(context: Context) {
    if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Here's my location: https://maps.google.com/")
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    } else {
        requestLocationPermission(context)
    }
}

fun requestLocationPermission(context: Context) {
    if (context is Activity) {
        ActivityCompat.requestPermissions(
            context,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION_CODE
        )
    }
}

fun showQrCode(context: Context) {
    // TODO: QR Code yaratish va ko'rsatish uchun kitobxonadan foydalaning
    Toast.makeText(context, "Show QR Code", Toast.LENGTH_SHORT).show()
}

fun shareContact(context: Context, contactName: String, contactPhone: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Contact: $contactName, Phone: $contactPhone")
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}
