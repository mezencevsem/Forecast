package com.mezencevsem.forecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mezencevsem.forecast.data.network.response.current.CurrentWeatherResponse
import com.mezencevsem.forecast.data.network.response.future.FutureWeatherResponse
import com.mezencevsem.forecast.internal.NoConnectivityExceptions

const val FORECAST_DAYS_COUNT = 10

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
        val languageCodeAdapted = languageCode.replace("en", "")
        try {
            val fetchCurrentWeather = apixuWeatherApiService
                .getCurrentWeather(
                    location,
                    unitSystemCode,
                    languageCodeAdapted
                )
                .await()

            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        } catch (e: NoConnectivityExceptions) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(
        location: String,
        unitSystemCode: String,
        languageCode: String
    ) {
        val languageCodeAdapted = languageCode.replace("en", "")
        try {
            val fetchFutureWeather = apixuWeatherApiService
                .getFutureWeather(
                    location,
                    FORECAST_DAYS_COUNT,
                    unitSystemCode,
                    languageCodeAdapted
                )
                .await()

            _downloadedFutureWeather.postValue(fetchFutureWeather)
        } catch (e: NoConnectivityExceptions) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}