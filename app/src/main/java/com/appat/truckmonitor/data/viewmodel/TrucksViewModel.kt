package com.appat.truckmonitor.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appat.truckmonitor.R
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.repository.TrucksRepository
import com.appat.truckmonitor.data.state.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrucksViewModel@Inject constructor(
    private val trucksRepository: TrucksRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    val trucksList = MutableStateFlow<NetworkResult<out List<Truck>>>(NetworkResult.Loading(R.string.loader_msg))
    fun getTrucks() = viewModelScope.launch(dispatcher) {
        trucksRepository.getTrucks().collect{
            trucksList.value = it
        }
    }
}