package com.baszczyk.mercpiggibank3.form

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercpiggibank3.database.entities.Mercedes
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class FormViewModel(val databaseDao: PiggyDatabaseDao,
                    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    suspend fun insertMercedes(mercedes: Mercedes) {
        withContext(Dispatchers.IO) {
            databaseDao.insertMercedes(mercedes)
        }
    }

    fun addMercedes(mercedes: Mercedes) {
        uiScope.launch{
            insertMercedes(mercedes)
        }
    }
val currentMercedes = MutableLiveData<Long?>()

   private suspend fun getMercedesId(): Long? {
       return withContext(Dispatchers.IO) {
           val mercedes = databaseDao.getMercedesId()
           mercedes
       }
    }

    fun mercedesId() {
        uiScope.launch {
            currentMercedes.value = getMercedesId()
        }
    }

    private suspend fun insertPiggyBank(piggyBank: PiggyBank) {
        withContext(Dispatchers.IO) {
            databaseDao.insertPiggyBank(piggyBank)
        }
    }

    fun addPiggyBank(piggyBank: PiggyBank) {
        uiScope.launch {
            insertPiggyBank(piggyBank)
        }
    }

}