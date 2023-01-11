package com.example.bloodpressurechecker.data.repository

import android.content.Context
import com.example.bloodpressurechecker.data.remote.BloodPressureInfo
import com.example.bloodpressurechecker.data.room.TrackerDao
import dagger.hilt.android.qualifiers.ApplicationContext
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


    suspend fun getRecord():List<BloodPressureInfo> {
        return local.getRecord()
    }

//
//    suspend fun getCurrentInfo():CurrentInfo {
//        return local.getCurrentData()
//    }

    suspend fun deleteHistory(id:Int):Int {
        return  local.delete(id)
    }

    suspend fun reset():Int {
        return  local.recordDelete()
    }


}
