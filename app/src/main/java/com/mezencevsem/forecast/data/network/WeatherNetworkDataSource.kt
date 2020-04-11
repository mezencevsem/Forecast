package com.mezencevsem.forecast.data.network

import androidx.lifecycle.LiveData
import com.mezencevsem.forecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        unitSystemCode: String,
        languageCode: String
    )
}