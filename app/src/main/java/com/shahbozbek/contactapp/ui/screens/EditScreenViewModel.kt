package com.shahbozbek.contactapp.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.shahbozbek.contactapp.data.ContactEntity
import com.shahbozbek.contactapp.repository.ContactRepository
import javax.inject.Inject

class EditScreenViewModel @Inject constructor(private val contactRepository: ContactRepository) :
    ViewModel() {

}