package com.baszczyk.mercpiggibank3.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class ListViewModel(dataSource: PiggyDatabaseDao, application: Application) : ViewModel() {
    val database = dataSource

    var piggies = MutableLiveData<List<PiggyBank>>()

    private suspend fun getAllPiggies(id: Long): List<PiggyBank> {
        return withContext(Dispatchers.IO) {
            database.getAllPiggies(id)
        }
    }

   suspend fun allPiggies(id: Long) {

            piggies.value = getAllPiggies(id)


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
