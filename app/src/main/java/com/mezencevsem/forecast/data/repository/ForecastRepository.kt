package com.mezencevsem.forecast.data.repository

import androidx.lifecycle.LiveData
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry
import com.mezencevsem.forecast.data.database.entity.FutureWeatherEntry
import com.mezencevsem.forecast.data.database.entity.WeatherLocation
import org.threeten.bp.LocalDate

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
    suspend fun getFutureWeatherList(startDate: LocalDate): LiveData<List<FutureWeatherEntry>>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}