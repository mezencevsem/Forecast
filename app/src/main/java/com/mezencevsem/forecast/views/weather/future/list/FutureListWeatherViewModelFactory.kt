package com.mezencevsem.forecast.views.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mezencevsem.forecast.data.provider.LanguageProvider
import com.mezencevsem.forecast.data.provider.UnitProvider
import com.mezencevsem.forecast.data.repository.ForecastRepository

class FutureListWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider,
    private val languageProvider: LanguageProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(
            forecastRepository,
            unitProvider,
            languageProvider
        ) as T
    }
}