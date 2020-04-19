package com.mezencevsem.forecast.views.base

import androidx.lifecycle.ViewModel
import com.mezencevsem.forecast.data.provider.LANGUAGE_ENGLISH_CODE
import com.mezencevsem.forecast.data.provider.LanguageProvider
import com.mezencevsem.forecast.data.provider.UnitProvider
import com.mezencevsem.forecast.data.repository.ForecastRepository
import com.mezencevsem.forecast.internal.UnitSystem
import com.mezencevsem.forecast.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider,
    languageProvider: LanguageProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()
    private val languageCode = languageProvider.getLanguageCode()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val isEnglishLanguage: Boolean
        get() = languageCode == LANGUAGE_ENGLISH_CODE

    val location by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}