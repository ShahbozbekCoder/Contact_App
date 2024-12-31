package com.shahbozbek.contactapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.shahbozbek.contactapp.data.ContactEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsScreen(viewModel: ContactViewModel) {
    val searchQuery = remember { mutableStateOf("") }
    val contacts = viewModel.contacts
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = "profile",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFEA8C8C))
                    .clickable { }
            )
            Text(
                text = "Contacts",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(R.drawable.edit),
                contentDescription = "edit",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Blue)
                    .padding(8.dp)
                    .clickable { },
                alignment = Alignment.TopEnd
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = { Text("Search",
                color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "search",
                    modifier = Modifier
                        .size(30.dp),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            },
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                contentColorFor(Color.White),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(10) {
                ContactInRow()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        val groupedContacts = groupContactsByInitial(contacts) // Guruhlash
        LazyColumn {
            groupedContacts.forEach { (initial, contactsForInitial) ->
                stickyHeader {
                    Text(
                        text = initial.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(8.dp),
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                }
                items(contactsForInitial.size) { contact ->
                    ContactRow(contactsForInitial[contact])
                }
            }
        }
    }
}

@Composable
fun ContactsList(contacts: List<ContactEntity>) {
    LazyColumn {
        items(contacts.size) { contact ->
            ContactRow(contacts[contact])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactsScreenPreview() {
    //ContactsScreen(viewModel = ContactViewModel(contactRepository))
}