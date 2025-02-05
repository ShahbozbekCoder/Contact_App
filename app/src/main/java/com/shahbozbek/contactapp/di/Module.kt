package com.shahbozbek.contactapp.di

import android.content.Context
import androidx.room.Room
import com.shahbozbek.contactapp.data.ContactDao
import com.shahbozbek.contactapp.data.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideContactDao(contactDatabase: ContactDatabase): ContactDao {
        return contactDatabase.contactDao()
    }

    @Singleton
    @Provides
    fun provideContactDatabase(@ApplicationContext applicationContext: Context): ContactDatabase {
        return Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contact_database"
        ).build()
    }
}