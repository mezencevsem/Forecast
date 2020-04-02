package com.mezencevsem.forecast.data.repository

import androidx.lifecycle.LiveData
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry
import com.mezencevsem.forecast.data.database.entity.WeatherLocation

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}