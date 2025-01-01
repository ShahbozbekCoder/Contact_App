package com.shahbozbek.contactapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahbozbek.contactapp.R
import com.shahbozbek.contactapp.data.ContactEntity
import com.shahbozbek.contactapp.util.ContactInRow
import com.shahbozbek.contactapp.util.ContactRow
import com.shahbozbek.contactapp.util.groupContactsByInitial
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    val groupedContacts = viewModel.groupedContacts
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.fetchContacts(context)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.searchContacts(it)
                },
                placeholder = {
                    Text(
                        "Search",
                        color = Color.Gray
                    )
                },
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
        }
        Row {
            LazyColumn(modifier = Modifier.weight(1f)) {
                groupedContacts.forEach { (initial, contactsForInitial) ->
                    stickyHeader {
                        Text(
                            text = initial.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE9EBEC))
                                .padding(4.dp),
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    items(contactsForInitial.size) { contact ->
                        ContactRow(contactsForInitial[contact])
                    }
                }
            }
            AlphabetScrollBar(onLetterSelected = { letter ->
                val index =
                    groupedContacts.keys.indexOfFirst { it.equals(letter, ignoreCase = true) }
                if (index > -0) {
                    coroutineScope.launch {
                        listState.scrollToItem(index)
                    }
                }
            })
        }
    }
}

@Composable
fun AlphabetScrollBar(onLetterSelected: (Char) -> Unit) {
    val alphabet = ('A'..'Z').toList()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        alphabet.forEach { letter ->
            Text(
                text = letter.toString(),
                fontSize = 10.sp,
                modifier = Modifier
                    .clickable { onLetterSelected(letter) }
            )
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