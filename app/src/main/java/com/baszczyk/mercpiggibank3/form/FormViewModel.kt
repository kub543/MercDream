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

    suspend fun addMercedes(mercedes: Mercedes) {

            insertMercedes(mercedes)

    }
val currentMercedes = MutableLiveData<Long?>()

   private suspend fun getMercedesId(): Long? {
       return withContext(Dispatchers.IO) {
           val mercedes = databaseDao.getMercedesId()
           mercedes
       }
    }

   suspend fun mercedesId() {

            currentMercedes.value = getMercedesId()

    }

    private suspend fun insertPiggyBank(piggyBank: PiggyBank) {
        withContext(Dispatchers.IO) {
            databaseDao.insertPiggyBank(piggyBank)
        }
    }

   suspend fun addPiggyBank(piggyBank: PiggyBank) {

            insertPiggyBank(piggyBank)

    }

}