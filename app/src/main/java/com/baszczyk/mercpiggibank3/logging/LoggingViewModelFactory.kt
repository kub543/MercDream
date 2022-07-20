package com.baszczyk.mercpiggibank3.logging

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao

class LoggingViewModelFactory (private val dataSource: PiggyDatabaseDao,
private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoggingViewModel::class.java)) {
            return LoggingViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}