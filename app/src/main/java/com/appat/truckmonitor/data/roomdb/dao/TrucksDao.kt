package com.appat.truckmonitor.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.appat.truckmonitor.data.models.Truck
import kotlinx.coroutines.flow.Flow


@Dao
interface TrucksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    suspend fun insertIgnore(truck: Truck): Long

    @Upsert
    @JvmSuppressWildcards
    suspend fun insertAll(trucks: List<Truck>?)

    @Query("SELECT * from Truck")
    fun getAllTrucks(): Flow<List<Truck>>

    @Query("DELETE FROM Truck")
    suspend fun deleteAllData()
}