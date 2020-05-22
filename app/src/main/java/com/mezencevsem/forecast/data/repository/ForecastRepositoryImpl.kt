package com.mezencevsem.forecast.data.repository

import androidx.lifecycle.LiveData
import com.mezencevsem.forecast.data.database.CurrentWeatherDAO
import com.mezencevsem.forecast.data.database.FutureWeatherDAO
import com.mezencevsem.forecast.data.database.RequestDAO
import com.mezencevsem.forecast.data.database.WeatherLocationDAO
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry
import com.mezencevsem.forecast.data.database.entity.FutureWeatherEntry
import com.mezencevsem.forecast.data.database.entity.Request
import com.mezencevsem.forecast.data.database.entity.WeatherLocation
import com.mezencevsem.forecast.data.network.FORECAST_DAYS_COUNT
import com.mezencevsem.forecast.data.network.WeatherNetworkDataSource
import com.mezencevsem.forecast.data.network.response.current.CurrentWeatherResponse
import com.mezencevsem.forecast.data.network.response.future.FutureWeatherResponse
import com.mezencevsem.forecast.data.provider.LanguageProvider
import com.mezencevsem.forecast.data.provider.LocationProvider
import com.mezencevsem.forecast.data.provider.UnitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val futureWeatherDAO: FutureWeatherDAO,
    private val weatherLocationDAO: WeatherLocationDAO,
    private val requestDAO: RequestDAO,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider,
    private val unitProvider: UnitProvider,
    private val languageProvider: LanguageProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDAO.getWeather()
        }
    }

    override suspend fun getFutureWeatherList(startDate: LocalDate): LiveData<List<FutureWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext futureWeatherDAO.getForecastWeather(startDate)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDAO.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDAO.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDAO.upsert(fetchedWeather.location)
            requestDAO.upsert(fetchedWeather.request)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {

        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDAO.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries
            futureWeatherDAO.insert(futureWeatherList.day1)
            futureWeatherDAO.insert(futureWeatherList.day2)
            futureWeatherDAO.insert(futureWeatherList.day3)
            futureWeatherDAO.insert(futureWeatherList.day4)
            futureWeatherDAO.insert(futureWeatherList.day5)
            futureWeatherDAO.insert(futureWeatherList.day6)
            futureWeatherDAO.insert(futureWeatherList.day7)
            futureWeatherDAO.insert(futureWeatherList.day8)
            futureWeatherDAO.insert(futureWeatherList.day9)
            futureWeatherDAO.insert(futureWeatherList.day10)
            weatherLocationDAO.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDAO.getLocationNonLive()
        val lastRequestInfo = requestDAO.getRequestInfo().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)
        ) {
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime, lastRequestInfo))
            fetchCurrentWeather()

        if (isFetchFutureNeeded(lastRequestInfo))
            fetchFutureWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            unitProvider.getUnitSystemCode(),
            languageProvider.getLanguageCode()
        )
    }

    private suspend fun fetchFutureWeather() {
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            unitProvider.getUnitSystemCode(),
            languageProvider.getLanguageCode()
        )
    }

    private fun isFetchCurrentNeeded(
        lastFetchedTime: ZonedDateTime,
        lastRequestInfo: Request?
    ): Boolean {
        if (lastRequestInfo == null) return true

        if (lastRequestInfo.languageCode != languageProvider.getLanguageCode() ||
            lastRequestInfo.unitSystemCode != unitProvider.getUnitSystemCode()) return true

        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchFutureNeeded(lastRequestInfo: Request?): Boolean {
        if (lastRequestInfo == null) return true
        if (lastRequestInfo.unitSystemCode != unitProvider.getUnitSystemCode()) return true

        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDAO.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }
}