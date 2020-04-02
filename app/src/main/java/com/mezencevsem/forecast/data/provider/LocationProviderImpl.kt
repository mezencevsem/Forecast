package com.mezencevsem.forecast.data.provider

import com.mezencevsem.forecast.data.database.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Kirishi"
    }
}