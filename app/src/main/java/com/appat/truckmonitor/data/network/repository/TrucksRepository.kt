package com.appat.truckmonitor.data.network.repository

import com.appat.truckmonitor.data.network.api.TrucksApi
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.network.state.ApiResponseHandler
import com.appat.truckmonitor.data.network.state.NetworkResult
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