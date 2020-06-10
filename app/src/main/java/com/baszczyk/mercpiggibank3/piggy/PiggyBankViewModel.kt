package com.baszczyk.mercpiggibank3.piggy

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baszczyk.mercpiggibank3.database.entities.Deposit
import com.baszczyk.mercpiggibank3.database.entities.Mercedes
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class PiggyBankViewModel(dataSource: PiggyDatabaseDao, application: Application) : ViewModel() {
    val database = dataSource

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val piggy = MutableLiveData<PiggyBank>()
    val mercedes = MutableLiveData<Mercedes>()

    private suspend fun getPiggy(id: Long): PiggyBank {
        return withContext(Dispatchers.IO) {
            val piggy = database.getPiggy(id)
            piggy
        }
    }

   suspend fun piggyGet(id: Long) {
            piggy.value = getPiggy(id)
    }

    private suspend fun getMercedes(id: Long): Mercedes {
        return withContext(Dispatchers.IO) {
           val mercedes = database.getMercedes(id)
            mercedes
        }
    }

   suspend fun mercedesGet(id: Long) {
            mercedes.value = getMercedes(id)
    }

    val deposit = MutableLiveData<Deposit>()

    private suspend fun insertDeposit(deposit: Deposit) {
        withContext(Dispatchers.IO) {
            database.insertDeposit(deposit)
        }
    }

    fun addDeposit(deposit: Deposit) {
        uiScope.launch {
            insertDeposit(deposit)
        }
    }

    private suspend fun updatePiggyBank(amount: Double, id: Long) {
        withContext(Dispatchers.IO) {
            database.updatePiggyBank(amount, id)

        }
    }

    fun updatePiggyActualAmount(amount: Double, id: Long) {
        uiScope.launch {
            updatePiggyBank(amount, id)
        }
    }

    private suspend fun deletePiggy(id: Long) {
        withContext(Dispatchers.IO) {
            database.deletePiggy(id)
        }
    }

    fun deletePiggies(id: Long) {
        uiScope.launch {
            deletePiggy(id)
        }
    }
}