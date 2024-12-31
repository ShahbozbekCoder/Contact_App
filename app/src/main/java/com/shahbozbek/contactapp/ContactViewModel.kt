package com.shahbozbek.contactapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.contactapp.data.ContactEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val contactRepository: ContactRepository) : ViewModel() {
    private val _contacts = mutableStateListOf<ContactEntity>()
    val contacts: List<ContactEntity> = _contacts

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
    fun searchContacts(query: String) {
        contactRepository.searchContacts(query)
        _contacts.clear()
        _contacts.addAll(contactRepository.searchContacts(query))
    }
    init {
        viewModelScope.launch {
            _contacts.addAll(contactRepository.getAllContacts())
        }
    }
}