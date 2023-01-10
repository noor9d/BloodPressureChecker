package com.example.bloodpressurechecker.data.remote

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "BloodPressureInfo" ,indices = [Index(value = ["id"], unique = true)])
data class BloodPressureInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val systolic:Int,
    val diastolic:Int,
    var pulse:Int,
    val date:String,
    val time:String,
    val status:String
)