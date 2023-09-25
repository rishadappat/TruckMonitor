package com.appat.truckmonitor.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appat.truckmonitor.R
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.network.repository.TrucksRepository
import com.appat.truckmonitor.data.network.state.NetworkResult
import com.appat.truckmonitor.data.roomdb.repository.TrucksDbRepository
import com.appat.truckmonitor.utilities.DateFormatString
import com.appat.truckmonitor.utilities.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TrucksViewModel@Inject constructor(
    private val trucksRepository: TrucksRepository,
    private val trucksDbRepository: TrucksDbRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(TrucksState())
    val uiState = _uiState.asStateFlow()

    suspend fun fetchTrucks() = viewModelScope.launch(dispatcher) {
        trucksRepository.getTrucks().collect {
            if (it is NetworkResult.Success) {
                trucksDbRepository.insertAll(it.data ?: listOf())
            } else if (it is NetworkResult.Error) {
                _uiState.update { currentState ->
                    currentState.copy(
                        truckResponse = NetworkResult.Error("Something went wrong", 0)
                    )
                }
            }
        }
    }

    suspend fun readTrucks() = viewModelScope.launch(dispatcher) {
        Timber.d(dispatcher.toString())
        Timber.d("Reading from database")
        trucksDbRepository.getAllTrucks().onStart {
            _uiState.update { currentState ->
                currentState.copy(
                    truckResponse = NetworkResult.Loading(R.string.loader_msg)
                )
            }
        }.catch {
            _uiState.update { currentState ->
                currentState.copy(
                    truckResponse = NetworkResult.Error("Something went wrong", 0)
                )
            }
        }.collect {
            _uiState.update { currentState ->
                currentState.copy(
                    truckResponse = NetworkResult.Success(it),
                    trucks = it
                )
            }
            searchTrucks()
            sortTrucks()
        }
    }

    fun toggleSort()
    {
        _uiState.update { currentState ->
            currentState.copy(
                isSorted = currentState.isSorted.not()
            )
        }
        sortTrucks()
    }

    private fun sortTrucks() {
        _uiState.update { currentState ->
            if (currentState.isSorted) {
                currentState.copy(
                    trucks = currentState.trucks.sortedByDescending {
                        DateUtils.stringToDate(it.lastUpdated, DateFormatString.defaultFormat)
                    }
                )
            } else {
                currentState.copy(
                    trucks = currentState.truckResponse.data?.filter {
                        (it.driverName ?: "").contains(currentState.searchText, true)
                    } ?: listOf()
                )
            }
        }
    }

    fun updateSearchString(searchText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchText = searchText
            )
        }
        searchTrucks()
    }

    private fun searchTrucks()
    {
        _uiState.update { currentState ->
            currentState.copy(
                trucks = currentState.truckResponse.data?.filter {
                    (it.driverName ?: "").contains(currentState.searchText, true)
                } ?: listOf()
            )
        }
    }
}

data class TrucksState (
    val truckResponse: NetworkResult<List<Truck>> = NetworkResult.Loading(R.string.loader_msg),
    val trucks: List<Truck> = listOf(),
    val searchText: String = "",
    val isSorted: Boolean = false
)