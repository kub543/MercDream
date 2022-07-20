package com.baszczyk.mercpiggibank3.form

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao
import kotlin.IllegalArgumentException

class FormViewModelFactory(private val dataSource: PiggyDatabaseDao,
                            private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            return FormViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}