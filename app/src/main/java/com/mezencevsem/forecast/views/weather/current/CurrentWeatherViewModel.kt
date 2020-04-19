package com.mezencevsem.forecast.views.weather.current

import com.mezencevsem.forecast.data.provider.LanguageProvider
import com.mezencevsem.forecast.data.provider.UnitProvider
import com.mezencevsem.forecast.data.repository.ForecastRepository
import com.mezencevsem.forecast.internal.lazyDeferred
import com.mezencevsem.forecast.views.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider,
    languageProvider: LanguageProvider
) : WeatherViewModel(forecastRepository, unitProvider, languageProvider) {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }
}
