package com.mezencevsem.forecast.data.network

import androidx.lifecycle.LiveData
import com.mezencevsem.forecast.data.network.response.current.CurrentWeatherResponse
import com.mezencevsem.forecast.data.network.response.future.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        unitSystemCode: String,
        languageCode: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        unitSystemCode: String,
        languageCode: String
    )
}