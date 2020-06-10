package com.baszczyk.mercpiggibank3.add_user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao
import com.baszczyk.mercpiggibank3.database.entities.User
import kotlinx.coroutines.*

class AddNewUserViewModel(val database: PiggyDatabaseDao,
                          application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
    }

    private suspend fun insertNewUser(user: User) {
        withContext(Dispatchers.IO) {
            database.insertNewUser(user)
        }
    }

   suspend fun addNewUser(user: User) {

            insertNewUser(user)

    }

    var currentUser = MutableLiveData<User>()

    suspend fun getNewUser() {

          currentUser.value = getCurrentUser()

    }

    private suspend fun getCurrentUser(): User {
        return withContext(Dispatchers.IO) {
            val user = database.getCurrentUser()
            user
        }
    }
}
