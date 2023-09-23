package com.appat.truckmonitor.data.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appat.truckmonitor.R
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.network.repository.TrucksRepository
import com.appat.truckmonitor.data.network.state.NetworkResult
import com.appat.truckmonitor.data.roomdb.repository.TrucksDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TrucksViewModel@Inject constructor(
    private val trucksRepository: TrucksRepository,
    private val trucksDbRepository: TrucksDbRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    var searchText = mutableStateOf(TextFieldValue(""))

    var isSorted = mutableStateOf(
        false
    )

    private val trucks = mutableStateListOf<Truck>()

    val trucksList = MutableStateFlow<NetworkResult<out List<Truck>>>(NetworkResult.Loading(R.string.loader_msg))
    suspend fun fetchTrucks() = viewModelScope.launch(dispatcher) {
        trucksRepository.getTrucks().collect {
            if(it is NetworkResult.Success) {
                trucksDbRepository.insertAll(it.data ?: listOf())
            }
            else if(it is NetworkResult.Error) {
                trucksList.value = it
            }
        }
    }

    suspend fun readTrucks() = viewModelScope.launch(dispatcher) {
        Timber.d(dispatcher.toString())
        Timber.d("Reading from database")
        trucksDbRepository.getAllTrucks().onStart {
            trucksList.value = NetworkResult.Loading(R.string.loader_msg)
        }.catch {
            trucksList.value = NetworkResult.Error("Something went wrong", 0)
        }.collect {
            trucksList.value = NetworkResult.Success(it)
            trucks.addAll(it)
        }
    }
}