package com.mezencevsem.forecast.data.network.response.future.unused

import com.google.gson.annotations.SerializedName
import com.mezencevsem.forecast.data.database.entity.FutureWeatherEntry

data class Forecast(
    @SerializedName("2020-04-14")
    val x20200414: FutureWeatherEntry
)