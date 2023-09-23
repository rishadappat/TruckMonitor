package com.appat.truckmonitor.data.roomdb.repository

import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.roomdb.dao.TrucksDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrucksDbRepository  @Inject constructor(private val trucksDao: TrucksDao) {
    fun getAllTrucks(): Flow<List<Truck>> = trucksDao.getAllTrucks()

    suspend fun insertAll(trucks: List<Truck>) {
        trucksDao.deleteAllData()
        trucksDao.insertAll(trucks)
    }
}