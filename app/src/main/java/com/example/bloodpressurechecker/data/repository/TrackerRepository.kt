package com.example.bloodpressurechecker.data.repository

import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bloodpressurechecker.data.remote.BloodPressureInfo
import com.example.bloodpressurechecker.data.room.TrackerDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

class TrackerRepository @Inject constructor(private val local: TrackerDao, @ApplicationContext context: Context) {

    val context = context

//    /*create list to fetch weather data both current or new location*/
//    private val _data = MutableLiveData<List<WeatherDetail>>()
//    val weatherData : LiveData<List<WeatherDetail>>
//        get() = _data

    suspend fun insertBloodPressure(data:BloodPressureInfo){
        local.insertWeather(data)
    }

//
//    suspend fun getHistory():List<History> {
//        return local.getHistoryInfo()
//    }

//
//    suspend fun getCurrentInfo():CurrentInfo {
//        return local.getCurrentData()
//    }
//
//    suspend fun deleteHistory(id:Int):Int {
//        return  local.delete(id)
//    }


}
