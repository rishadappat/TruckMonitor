package com.appat.truckmonitor.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.repository.TrucksRepository
import com.appat.truckmonitor.data.state.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrucksViewModel@Inject constructor(
    private val trucksRepository: TrucksRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    var trucksList: Flow<NetworkResult<out List<Truck>>>? = null
    fun getTrucks() = viewModelScope.launch(dispatcher) {
        trucksList = trucksRepository.getTrucks()
    }
}