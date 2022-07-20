package com.baszczyk.mercpiggibank3.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baszczyk.mercpiggibank3.network.MercedesApi
import com.baszczyk.mercpiggibank3.network.MercedesApiFilter
import com.baszczyk.mercpiggibank3.network.MercedesApiService
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

    private val _navigateToSelectedProperty = MutableLiveData<MercedesPhoto>()
    val navigateToSelectedProperty: LiveData<MercedesPhoto>
        get() = _navigateToSelectedProperty

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMercedesPhoto(MercedesApiFilter.SHOW_ALL)
    }

    private fun getMercedesPhoto(filter: MercedesApiFilter) {
        coroutineScope.launch {
            _status.value = MercedesApiStatus.LOADING
            var getPropertiesDeferred = MercedesApi.retrofitService.getProperties(filter.value)
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

    fun updateFilter(filter: MercedesApiFilter) {
        getMercedesPhoto(filter)
    }

    fun displayPropertyDetails(mercedesPhoto: MercedesPhoto) {
        _navigateToSelectedProperty.value = mercedesPhoto
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}