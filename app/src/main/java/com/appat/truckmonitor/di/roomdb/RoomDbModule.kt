package com.appat.truckmonitor.di.roomdb

import android.content.Context
import com.appat.truckmonitor.data.roomdb.TruckMonitorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomDbModule {
    @Singleton
    @Provides
    fun providesTruckMonitorDatabase(@ApplicationContext appContext: Context): TruckMonitorDatabase {
        return TruckMonitorDatabase.getInstance(appContext)
    }
}