package com.shahbozbek.contactapp.repository

import com.shahbozbek.contactapp.data.ContactDao
import com.shahbozbek.contactapp.data.ContactEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(private val contactDao: ContactDao) {

    fun getAllContacts() = contactDao.getAllContacts()

    suspend fun insert(contact: ContactEntity) = contactDao.insertContact(contact)

    suspend fun delete(contact: ContactEntity) = contactDao.deleteContact(contact)

    suspend fun update(contact: ContactEntity) = contactDao.updateContact(contact)

    fun searchContacts(query: String) = contactDao.searchContacts(query)

}