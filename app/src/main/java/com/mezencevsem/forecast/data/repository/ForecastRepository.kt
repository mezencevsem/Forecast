package com.mezencevsem.forecast.data.repository

import androidx.lifecycle.LiveData
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
}