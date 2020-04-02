package com.mezencevsem.forecast.data.provider

import com.mezencevsem.forecast.data.database.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}