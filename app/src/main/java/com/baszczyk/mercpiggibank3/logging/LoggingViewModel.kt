package com.baszczyk.mercpiggibank3.logging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.database.PiggyDatabaseDao
import com.baszczyk.mercpiggibank3.database.User
import kotlinx.coroutines.*

class LoggingViewModel(val database: PiggyDatabaseDao,
                       application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)



    override fun onCleared() {
        super.onCleared()
    }


    var currentUser = MutableLiveData<User?>()

    fun getUser(name: String){
        uiScope.launch {
            currentUser.value = getCurrentUser(name)
        }
    }

    private suspend fun getCurrentUser(name: String): User?{
        return withContext(Dispatchers.IO){
            val user = database.getUser(name)
            user
        }
    }



    lateinit var piggies: List<Long?>

    fun getAllPiggies(userId: Long){
        uiScope.launch {
            piggies = allPiggies(userId)
        }
    }

    private suspend fun allPiggies(userId: Long):List<Long?>{
        return withContext(Dispatchers.IO){
            val piggie = database.getPiggyId(userId)
            piggie
        }
    }



    lateinit var users: List<String>

    fun getAllUsersNames(){
        uiScope.launch {
            users = allUsersNames()
        }
    }
    private suspend fun allUsersNames(): List<String>{
        return withContext(Dispatchers.IO){
            var names = database.getAllUsersName()
            names
        }
    }

    lateinit var userPassword: String

    fun getUserPassword(userName: String) {
        uiScope.launch {
            userPassword = getPassword(userName)
        }
    }

    private suspend fun getPassword(userName: String): String {
        return withContext(Dispatchers.IO) {
            val password = database.getUserPassword(userName)
            password
        }
    }



}