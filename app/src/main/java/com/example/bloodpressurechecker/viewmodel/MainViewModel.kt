package com.example.bloodpressurechecker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressurechecker.data.remote.BloodPressureInfo
import com.example.bloodpressurechecker.data.repository.TrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TrackerRepository) : ViewModel() {



    /* create list to fetch Weather History*/

     val _bloodInfo = MutableLiveData<List<BloodPressureInfo>>()
    val bloodInfo: LiveData<List<BloodPressureInfo>>
        get() = _bloodInfo
//
//    /* create list to fetch current location data to show in history Screen*/
//    private val _currentInfo = MutableLiveData<CurrentInfo>()
//    val currentInfo: LiveData<CurrentInfo>
//        get() = _currentInfo
//
//
    fun insertData(data:BloodPressureInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertBloodPressure(data)
        }
    }

    fun readRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            _bloodInfo.postValue(repository.getRecord())
        }
    }

//    fun refreshCurrentWeather(){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getCurrentWeather()
//        }
//    }

    fun deleteLocation(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val number=repository.deleteHistory(id)
        }.invokeOnCompletion {
            viewModelScope.launch(Dispatchers.IO){
//                readHistory()
//                refreshCurrentWeather()
            }
        }
    }


    fun resetRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            val number=repository.reset()
        }.invokeOnCompletion {
            viewModelScope.launch(Dispatchers.IO){
//                readHistory()
//                refreshCurrentWeather()
            }
        }
    }

}

