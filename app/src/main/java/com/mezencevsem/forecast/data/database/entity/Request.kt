package com.mezencevsem.forecast.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val WEATHER_REQUEST_INFO_ID = 0

@Entity(tableName = "weather_request_info")
data class Request(
    @SerializedName("language")
    val languageCode: String,
    val query: String,
    val type: String,
    @SerializedName("unit")
    val unitSystemCode: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_REQUEST_INFO_ID
}