package com.baszczyk.mercpiggibank3.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class ListViewModel(dataSource: PiggyDatabaseDao, application: Application) : ViewModel() {
    val database = dataSource

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var piggies = MutableLiveData<List<PiggyBank>>()

    private suspend fun getAllPiggies(id: Long): List<PiggyBank> {
        return withContext(Dispatchers.IO) {
            database.getAllPiggies(id)
        }
    }

    fun allPiggies(id: Long) {
        uiScope.launch {
            piggies.value = getAllPiggies(id)
        }

    }

    private val _navigateToPiggyDetail = MutableLiveData<Long>()
    val navigateToPiggyDetails
        get() = _navigateToPiggyDetail

    fun onPiggyBankClicked(id: Long) {
        _navigateToPiggyDetail.value = id
    }

    fun onPiggyDetailNavigated() {
        _navigateToPiggyDetail.value = null
    }
}
