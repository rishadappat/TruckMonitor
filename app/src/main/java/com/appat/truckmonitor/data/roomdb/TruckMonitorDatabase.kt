package com.appat.truckmonitor.data.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.roomdb.dao.TrucksDao


@Database(
    entities = [Truck::class],
    version = 1,
    exportSchema = false
)
abstract class TruckMonitorDatabase: RoomDatabase() {

    abstract fun trucksDao(): TrucksDao
    companion object {
        @Volatile private var instance: TruckMonitorDatabase? = null
        private const val DATABASE_NAME = "truckmonitor"

        fun getInstance(context: Context): TruckMonitorDatabase {
            if (instance == null) {
                try {
                    instance = Room.databaseBuilder(context, TruckMonitorDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                    return instance as TruckMonitorDatabase
                }
            }
            return instance as TruckMonitorDatabase
        }
    }

}