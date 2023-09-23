package com.appat.truckmonitor.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Truck")
data class Truck(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int,

    @ColumnInfo
    val driverName: String? = "",

    @ColumnInfo
    val imageURL: String? = "",

    @ColumnInfo
    val lastUpdated: String? = "",

    @ColumnInfo
    val lat: Double = 0.0,

    @ColumnInfo
    val lng: Double = 0.0,

    @ColumnInfo
    val location: String? = "",

    @ColumnInfo
    val plateNo: String? = ""
)