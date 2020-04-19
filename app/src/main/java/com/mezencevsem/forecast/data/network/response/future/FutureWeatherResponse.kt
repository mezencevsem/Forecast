package com.mezencevsem.forecast.data.network.response.future

import com.google.gson.annotations.SerializedName
import com.mezencevsem.forecast.data.database.entity.FutureWeatherEntry
import com.mezencevsem.forecast.data.database.entity.WeatherLocation

data class FutureWeatherResponse(
    //val current: Current,
    //val request: Request,
    val location: WeatherLocation,
    @SerializedName("forecast")
    val futureWeatherEntries: List<FutureWeatherEntry>
)