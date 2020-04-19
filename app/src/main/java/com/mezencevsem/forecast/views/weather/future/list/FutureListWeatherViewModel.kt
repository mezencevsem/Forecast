package com.mezencevsem.forecast.views.weather.future.list

import com.mezencevsem.forecast.data.provider.LanguageProvider
import com.mezencevsem.forecast.data.provider.UnitProvider
import com.mezencevsem.forecast.data.repository.ForecastRepository
import com.mezencevsem.forecast.internal.lazyDeferred
import com.mezencevsem.forecast.views.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider,
    languageProvider: LanguageProvider
) : WeatherViewModel(forecastRepository, unitProvider, languageProvider) {

    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(LocalDate.now())
    }
}
