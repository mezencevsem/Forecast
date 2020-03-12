package com.mezencevsem.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry
import com.mezencevsem.forecast.data.database.entity.Location
import com.mezencevsem.forecast.data.database.entity.Request


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)