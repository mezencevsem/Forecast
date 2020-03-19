package com.mezencevsem.forecast.views.weather.current

import androidx.lifecycle.ViewModel
import com.mezencevsem.forecast.data.repository.ForecastRepository
import com.mezencevsem.forecast.internal.UnitSystem
import com.mezencevsem.forecast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }
}
