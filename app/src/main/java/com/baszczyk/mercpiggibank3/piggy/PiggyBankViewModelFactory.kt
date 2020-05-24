package com.baszczyk.mercpiggibank3.piggy

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao

class PiggyBankViewModelFactory (private val dataSource: PiggyDatabaseDao,
                                 private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PiggyBankViewModel::class.java)) {
            return PiggyBankViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}