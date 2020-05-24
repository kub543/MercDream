package com.baszczyk.mercpiggibank3.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercpiggibank3.database.entities.Deposit
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class HistoryViewModel(val database: PiggyDatabaseDao,
                       application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var deposit = MutableLiveData<List<Deposit?>>()

    private suspend fun getAllPiggiesDeposits(id: Long): List<Deposit?> {
        return withContext(Dispatchers.IO) {
            val deposit = database.getAllDepositsForCurrentPiggy(id)
            deposit
        }
    }

     fun piggyDepositId(id: Long) {
        uiScope.launch {
            deposit.value = getAllPiggiesDeposits(id)
        }
    }

    private suspend fun getAllDeposit(id: Long): List<Deposit> {
        return withContext(Dispatchers.IO) {
            val allDeposit = database.getAllDepositsForCurrentUser(id)
            allDeposit
        }
    }

    fun allDeposits(id: Long) {
        uiScope.launch {
            deposit.value = getAllDeposit(id)
        }
    }
}