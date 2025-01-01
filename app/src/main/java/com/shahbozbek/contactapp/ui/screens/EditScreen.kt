package com.shahbozbek.contactapp.ui.screens

import android.Manifest
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shahbozbek.contactapp.R
import com.shahbozbek.contactapp.data.ContactEntity

@Composable
fun ContactDetailScreen(
    navController: NavController? = null,
    contact: ContactEntity? = null,
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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
                navController?.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            IconButton(onClick = { /* Edit contact */ }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color(0xFF1E88E5)
                )
            }
        }

        // Contact Avatar va Name
        if (contact != null) {
            Image(
                painter = rememberAsyncImagePainter(contact.avatarUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = contact.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = contact.phoneNumber, color = Color.Gray)
        }

        // Main Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            contact?.let {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF00BA88))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { sendMessage(context, contact.phoneNumber) }) {
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
                    IconButton(onClick = { makeCall(context, contact.phoneNumber) }) {
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
                    IconButton(onClick = { startVideoCall(context, contact.phoneNumber) }) {
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
                contact?.let {
                    ContactInfoRow(
                        label = "Mobile",
                        value = it.phoneNumber,
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
            }
            item {
                contact?.let {
                    ContactInfoRow(
                        label = "Home",
                        value = "(405) 555-0128",
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
            }
            item {
                contact?.let {
                    ContactInfoRow(
                        label = "Work",
                        value = "(907) 555-0128",
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
            }
            item {
                contact?.let {
                    ContactInfoRow(
                        label = "Work",
                        value = "(907) 555-0128",
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
            }
            item {
                contact?.let {
                    ContactInfoRow(
                        label = "Work",
                        value = "(907) 555-0128",
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
            }
            item {
                contact?.let {
                    ContactInfoRow(
                        label = "Work",
                        value = "(907) 555-0128",
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
            }
            item {
                contact?.let {
                    ContactInfoRow(
                        label = "Work",
                        value = "(907) 555-0128",
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
            }
            item {
                contact?.let {
                    ContactInfoRow(
                        label = "Work",
                        value = "(907) 555-0128",
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
            }
            item {
                contact?.let {
                    ContactInfoRow(
                        label = "Work",
                        value = "(907) 555-0128",
                        onCallClick = { makeCall(context, contact.phoneNumber) },
                        onMessageClick = { sendMessage(context, contact.phoneNumber) }
                    )
                }
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
                            contact ?: ContactEntity(0, "", "", "")
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
                Icon(imageVector = Icons.Default.Call, contentDescription = "Call",
                    tint = Color(0xFF4CAF50))
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

}

fun sendMessage(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("sms:$phoneNumber")
    }
    context.startActivity(intent)
}

fun startVideoCall(context: Context, phoneNumber: String) {
    // TODO: O'zingizning video qo'ng'iroq funksiyasiga mos API ishlating
    Toast.makeText(context, "Video Call to $phoneNumber", Toast.LENGTH_SHORT).show()
}

fun shareLocation(context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Here's my location: https://maps.google.com/")
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}

fun showQrCode(context: Context) {
    // TODO: QR Code yaratish va ko'rsatish uchun kitobxonadan foydalaning
    Toast.makeText(context, "Show QR Code", Toast.LENGTH_SHORT).show()
}

fun shareContact(context: Context, contact: ContactEntity) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Contact: ${contact.name}, Phone: ${contact.phoneNumber}")
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}

@Preview(showBackground = true)
@Composable
fun ContactDetailScreenPreview() {
    ContactDetailScreen(
        navController = null,
        ContactEntity(
            name = "John Doe",
            phoneNumber = "123-456-7890",
            avatarUrl = "https://i.pinimg.com/originals/70/65/3e/70653e2f2e0fc4960ba10357747fb1b9.jpg"
        )
    )
}
