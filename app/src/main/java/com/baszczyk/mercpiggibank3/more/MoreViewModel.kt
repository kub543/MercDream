package com.baszczyk.mercpiggibank3.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baszczyk.mercpiggibank3.network.MercedesApi
import com.baszczyk.mercpiggibank3.network.MercedesPhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class MercedesApiStatus { LOADING, ERROR, DONE }

class MoreViewModel : ViewModel() {
    private val _status = MutableLiveData<MercedesApiStatus>()

    val status: LiveData<MercedesApiStatus>
    get() = _status

    private val _photos = MutableLiveData<List<MercedesPhoto>>()

    val photos: LiveData<List<MercedesPhoto>>
    get() = _photos

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMercedesPhoto()
    }

    private fun getMercedesPhoto() {
        coroutineScope.launch {
            _status.value = MercedesApiStatus.LOADING
            var getPropertiesDeferred = MercedesApi.retrofitService.getProperties()
            try {
                var listResult = getPropertiesDeferred.await()
                    _status.value = MercedesApiStatus.DONE
                    _photos.value = listResult

            } catch (e: Exception) {
                _status.value = MercedesApiStatus.ERROR
                _photos.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}