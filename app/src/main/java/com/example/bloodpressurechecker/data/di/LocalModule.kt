package com.example.bloodpressurechecker.data.di

import android.content.Context
import androidx.room.Room
import com.example.bloodpressurechecker.data.room.TrackerDao
import com.example.bloodpressurechecker.data.room.TrackerDataBase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): TrackerDataBase {
        return Room.databaseBuilder(
            appContext,
            TrackerDataBase::class.java,
            "TrackerDB")
            .build()
    }

    @Provides
    fun provideChannelDao(weatherDataBase: TrackerDataBase): TrackerDao {
        return weatherDataBase.TrackerDao()
    }
}