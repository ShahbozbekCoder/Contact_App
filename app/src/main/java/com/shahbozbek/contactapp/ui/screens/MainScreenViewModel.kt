package com.shahbozbek.contactapp.ui.screens

import android.content.Context
import android.provider.ContactsContract
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.contactapp.repository.ContactRepository
import com.shahbozbek.contactapp.data.ContactEntity
import com.shahbozbek.contactapp.util.groupContactsByInitial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val contactRepository: ContactRepository) : ViewModel() {
    private val _contacts = mutableStateListOf<ContactEntity>()
    val contacts: List<ContactEntity> get() = _contacts

    private val _filteredContacts = mutableStateListOf<ContactEntity>()
    val filteredContacts: List<ContactEntity> get() = _filteredContacts

    val groupedContacts: Map<Char, List<ContactEntity>>
        get() = groupContactsByInitial(_contacts)

    fun getAllContacts() = contactRepository.getAllContacts()

    fun insert(contact: ContactEntity) = viewModelScope.launch {
        contactRepository.insert(contact)

    }
    fun delete(contact: ContactEntity) = viewModelScope.launch {
        contactRepository.delete(contact)
    }
    fun update(contact: ContactEntity) = viewModelScope.launch {
        contactRepository.update(contact)

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
        _contacts.clear()
        _contacts.addAll(contactList)
        _filteredContacts.clear()
        _filteredContacts.addAll(contactList)
        return contactList
    }
    fun searchContacts(query: String) {
        _filteredContacts.clear()
        val filteredList = _contacts.filter { contact ->
            contact.name.contains(query, ignoreCase = true) ||
                    contact.phoneNumber.contains(query, ignoreCase = true)
        }
        _filteredContacts.addAll(filteredList)
    }
}
