package com.mezencevsem.forecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mezencevsem.forecast.data.network.response.CurrentWeatherResponse
import com.mezencevsem.forecast.internal.NoConnectivityExceptions

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(
        location: String,
        unitSystemCode: String,
        languageCode: String
    ) {
        try {
            val fetchCurrentWeather = apixuWeatherApiService
                .getCurrentWeather(
                    location,
                    unitSystemCode,
                    languageCode.replace("en", ""))
                .await()

            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        } catch (e: NoConnectivityExceptions) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}