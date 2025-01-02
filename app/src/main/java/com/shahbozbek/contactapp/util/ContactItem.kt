package com.shahbozbek.contactapp.util

import android.content.Context
import android.provider.CallLog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.shahbozbek.contactapp.R
import com.shahbozbek.contactapp.data.ContactEntity

@Composable
fun ContactInRow(contacts: List<ContactEntity>) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(contacts.size) { contact ->
            Column(
                modifier = Modifier
                    .height(180.dp)
                    .width(146.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Card(
                        modifier = Modifier.size(130.dp),
                        onClick = {
                            // Qo'ng'iroq qilingan kontaktni tanlash
                        },
                        shape = RoundedCornerShape(32.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (contacts[contact].avatarUrl != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(contacts[contact].avatarUrl),
                                    contentDescription = "contact",
                                    contentScale = ContentScale.Crop,
                                )
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.img_3),
                                    contentDescription = "default contact",
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = contacts[contact].name,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .width(100.dp),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Last call: ${contacts[contact].lastCallTime}", // Qo'ng'iroq vaqti
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
            }
        }
    }
}


fun fetchRecentCalls(context: Context): List<ContactEntity> {
    val contacts = mutableListOf<ContactEntity>()
    val cursor = context.contentResolver.query(
        CallLog.Calls.CONTENT_URI,
        arrayOf(
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE
        ),
        null,
        null,
        "${CallLog.Calls.DATE} DESC"
    )
    cursor?.use {
        while (it.moveToNext()) {
            val name = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME)) ?: "Unknown"
            val number = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER)) ?: "Unknown"
            val date = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE))
            val avatarUrl = null // O'z ro'yxatingizga mos holda URLni qo'shing

            contacts.add(ContactEntity(name = name, phoneNumber = number, avatarUrl = avatarUrl, lastCallTime = date))
        }
    }
    return contacts
}
