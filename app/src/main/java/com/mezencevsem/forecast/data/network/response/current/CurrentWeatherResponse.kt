package com.mezencevsem.forecast.data.network.response.current

import com.google.gson.annotations.SerializedName
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry
import com.mezencevsem.forecast.data.database.entity.Request
import com.mezencevsem.forecast.data.database.entity.WeatherLocation

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation,
    val request: Request
)