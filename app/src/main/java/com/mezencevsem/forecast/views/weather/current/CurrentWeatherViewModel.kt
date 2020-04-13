package com.mezencevsem.forecast.views.weather.current

import androidx.lifecycle.ViewModel
import com.mezencevsem.forecast.data.provider.LANGUAGE_ENGLISH_CODE
import com.mezencevsem.forecast.data.provider.LanguageProvider
import com.mezencevsem.forecast.data.provider.UnitProvider
import com.mezencevsem.forecast.data.repository.ForecastRepository
import com.mezencevsem.forecast.internal.UnitSystem
import com.mezencevsem.forecast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider,
    languageProvider: LanguageProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()
    private val languageCode = languageProvider.getLanguageCode()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val isEnglish: Boolean
        get() = languageCode == LANGUAGE_ENGLISH_CODE

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }

    val location by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}
