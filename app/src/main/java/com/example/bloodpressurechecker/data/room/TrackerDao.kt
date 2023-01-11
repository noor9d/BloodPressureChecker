package com.example.bloodpressurechecker.data.room

import androidx.room.*
import com.example.bloodpressurechecker.data.remote.BloodPressureInfo

@Dao
interface TrackerDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertWeather(data: BloodPressureInfo):Long

//
//    @Update
//    suspend fun updateWeatherDetail(weather: WeatherDetail)

    @Query("select * from  BloodPressureInfo")
    suspend fun getRecord(): List<BloodPressureInfo>
//
//    @Query("select * from  weatherdetail where status ='Current' ")
//    suspend fun getRefreshWeather(): List<WeatherDetail>
//
//    @Query("select  ids,address , status ,dt, weather ,`temp`,lat,lon ,timezone,clouds  from  weatherdetail where status ='Search'  order by dt desc")
//    suspend fun getHistoryInfo(): List<History>
//
//    @Query("select  address , status ,dt, weather ,`temp` ,timezone from  weatherdetail where status ='Current'")
//    suspend fun getCurrentData(): CurrentInfo
//
    @Query("DELETE FROM BloodPressureInfo WHERE id = :id")
    suspend fun delete(id:Int):Int

    @Query("DELETE FROM BloodPressureInfo")
    suspend fun recordDelete():Int
//
//    @Query("select * from weatherdetail where address=:address and status='Search'")
//    suspend fun isAddressFound(address:String): List<WeatherDetail>
}