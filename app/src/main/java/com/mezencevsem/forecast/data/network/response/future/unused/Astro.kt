package com.mezencevsem.forecast.data.network.response.future.unused


import com.google.gson.annotations.SerializedName

data class Astro(
    @SerializedName("moon_illumination")
    val moonIllumination: Int,
    @SerializedName("moon_phase")
    val moonPhase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)