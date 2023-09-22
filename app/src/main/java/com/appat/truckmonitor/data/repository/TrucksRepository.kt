package com.appat.truckmonitor.data.repository

import com.appat.truckmonitor.data.api.TrucksApi
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.state.ApiResponseHandler
import com.appat.truckmonitor.data.state.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrucksRepository @Inject constructor(private val apiService: TrucksApi,
                                           private val defaultDispatcher: CoroutineDispatcher):
    ApiResponseHandler() {
    suspend fun getTrucks(): Flow<NetworkResult<out List<Truck>>> {
        return safeApiCall(defaultDispatcher) {
            apiService.getTrucks()
        }
    }
}