package com.appat.truckmonitor.di.roomdb

import com.appat.truckmonitor.data.roomdb.TruckMonitorDatabase
import com.appat.truckmonitor.data.roomdb.dao.TrucksDao
import com.appat.truckmonitor.data.roomdb.repository.TrucksDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TrucksDbModule {
    @Singleton
    @Provides
    fun providesTrucksDao(truckMonitorDatabase: TruckMonitorDatabase): TrucksDao {
        return truckMonitorDatabase.trucksDao()
    }

    @Singleton
    @Provides
    fun providesTrucksDbRepository(trucksDao: TrucksDao): TrucksDbRepository {
        return TrucksDbRepository(trucksDao)
    }
}