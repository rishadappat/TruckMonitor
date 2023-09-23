package com.appat.truckmonitor.data.network.api

import com.appat.truckmonitor.data.models.Truck
import retrofit2.Response
import retrofit2.http.GET

interface TrucksApi {
    @GET("candidate")
    suspend fun getTrucks(): Response<List<Truck>>
}