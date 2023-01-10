package com.example.bloodpressurechecker.data.room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bloodpressurechecker.data.remote.BloodPressureInfo

@Database(entities = [BloodPressureInfo::class ], version = 1)

abstract class TrackerDataBase : RoomDatabase() {

    abstract fun TrackerDao(): TrackerDao

}