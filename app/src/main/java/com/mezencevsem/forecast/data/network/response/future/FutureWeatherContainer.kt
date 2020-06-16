package com.mezencevsem.forecast.data.network.response.future

import com.google.gson.annotations.SerializedName
import com.mezencevsem.forecast.data.database.entity.FutureWeatherEntry

data class FutureWeatherContainer(
    @SerializedName(day0date)
    val day0: FutureWeatherEntry,
    @SerializedName(day1date)
    val day1: FutureWeatherEntry,
    @SerializedName(day2date)
    val day2: FutureWeatherEntry,
    @SerializedName(day3date)
    val day3: FutureWeatherEntry,
    @SerializedName(day4date)
    val day4: FutureWeatherEntry,
    @SerializedName(day5date)
    val day5: FutureWeatherEntry,
    @SerializedName(day6date)
    val day6: FutureWeatherEntry,
    @SerializedName(day7date)
    val day7: FutureWeatherEntry,
    @SerializedName(day8date)
    val day8: FutureWeatherEntry,
    @SerializedName(day9date)
    val day9: FutureWeatherEntry
) {
    //TODO trash
    companion object {
        const val day0date = "2020-06-16"
        const val day1date = "2020-06-17"
        const val day2date = "2020-06-18"
        const val day3date = "2020-06-19"
        const val day4date = "2020-06-20"
        const val day5date = "2020-06-21"
        const val day6date = "2020-06-22"
        const val day7date = "2020-06-23"
        const val day8date = "2020-06-24"
        const val day9date = "2020-06-25"
    }
}